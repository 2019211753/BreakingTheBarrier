package com.lrm.service;

import com.lrm.dao.InfoEntryRepository;
import com.lrm.exception.FailedOperationException;
import com.lrm.exception.NotFoundException;
import com.lrm.po.InfoEntry;
import com.lrm.util.LockHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Deque;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class InfoEntryServiceImpl implements InfoEntryService {
    private Logger logger = LoggerFactory.getLogger(InfoEntryServiceImpl.class);

    @Autowired
    private InfoEntryRepository infoEntryRepository;

    @Override
    public InfoEntry saveInfoEntry(InfoEntry infoEntry) {
        return infoEntryRepository.save(infoEntry);
    }

    @Override
    public void deleteEntry(Long entryId) {
        Optional<InfoEntry> entry = infoEntryRepository.findById(entryId);
        if (!entry.isPresent())
            throw new NotFoundException("未发现指定词条");
        infoEntryRepository.delete(entry.get());
    }

    @Override
    public Page<InfoEntry> searchEntry(int pageIndex, String title) {
        if (title == null) return null;
        return infoEntryRepository.findByTitle(title, PageRequest.of(pageIndex, 9));
    }

    @Override
    public Page<InfoEntry> getUnapproved(int pageIndex) {
        return infoEntryRepository.findUnapproved(PageRequest.of(pageIndex, 9));
    }

    @Override
    public Page<InfoEntry> getApprovedByTime(int pageIndex) {
        return infoEntryRepository.findTop5ByisApprovedOrderByLastApprovedTimeDesc(true, PageRequest.of(pageIndex, 5));
    }

    @Override
    @Transactional
    public InfoEntry approve(Long entryId) {
        Optional<InfoEntry> found = infoEntryRepository.findById(entryId);
        if (!found.isPresent()) {
            throw new NotFoundException("此词条不存在");
        }
        InfoEntry infoEntry = found.get();
        infoEntry.setCurrentContent(infoEntry.getNewContent());
        infoEntry.setNewContent(null);
        infoEntry.setLastApprovedTime(new Timestamp(System.currentTimeMillis()));
        infoEntry.setApproved(true);
        infoEntry.setLocked(false);//解锁
        return infoEntry;
    }

    @Override
    @Transactional
    public InfoEntry disapprove(Long entryId) {
        
        Optional<InfoEntry> found = infoEntryRepository.findById(entryId);
        if (!found.isPresent()) {
            throw new NotFoundException("此词条不存在");
        }
        InfoEntry infoEntry = found.get();
//        infoEntry.setCurrentContent(infoEntry.getNewContent());
        infoEntry.setNewContent(null);
//        infoEntry.setLastApprovedTime(new Timestamp(System.currentTimeMillis()));
        //未通过，但需要显示，approved
        infoEntry.setApproved(true);
        infoEntry.setLocked(false);//解锁
        return infoEntry;
    }



    @Override
    @Transactional
    public InfoEntry update(InfoEntry newEntry) {
        Optional<InfoEntry> found = infoEntryRepository.findById(newEntry.getId());
        if (!found.isPresent()) {
            throw new NotFoundException("此词条不存在");
        }
        InfoEntry infoEntry = found.get();
        if (infoEntry.isLocked()) {
            throw new FailedOperationException("词条被锁住");
        }
        infoEntry.setNewContent(newEntry.getNewContent());
        infoEntry.setApproved(false);//更新后有待审核
        infoEntry.setLocked(true);//暂时锁住
        return infoEntry;
    }

    @Override
    public InfoEntry getInfoEntry(Long infoEntryId) {
        Optional<InfoEntry> optionalInfoEntry = infoEntryRepository.findById(infoEntryId);
        return optionalInfoEntry.orElse(null);
    }

}
