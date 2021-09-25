package com.lrm.service;

import com.lrm.dao.InfoEntryRepository;
import com.lrm.exception.NotFoundException;
import com.lrm.po.InfoEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Optional;

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
    public Page<InfoEntry> getAllUnapproved() {
        return infoEntryRepository.findUnapproved(PageRequest.of(0, 9));
    }

    @Override
    public Page<InfoEntry> getApprovedByTime() {
        return infoEntryRepository.findTop5ByOrderByLastApprovedTimeDesc(PageRequest.of(0, 5));
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
    public InfoEntry update(InfoEntry newEntry) {
        Optional<InfoEntry> found = infoEntryRepository.findById(newEntry.getId());
        if (!found.isPresent()) {
            throw new NotFoundException("此词条不存在");
        }
        InfoEntry infoEntry = found.get();
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
