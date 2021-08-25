package com.lrm.service;

import com.lrm.dao.InfoEntryRepository;
import com.lrm.po.InfoEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class InfoEntryServiceImpl implements InfoEntryService{
    @Autowired
    private InfoEntryRepository infoEntryRepository;

    @Override
    public InfoEntry saveInfoEntry(InfoEntry infoEntry) {
        return infoEntryRepository.save(infoEntry);
    }

    @Override
    @Transactional
    public InfoEntry update(InfoEntry infoEntry) {
        infoEntry.setCurrentContent(infoEntry.getNewContent());
        infoEntry.setNewContent(null);
        return infoEntry;
    }

    @Override
    public InfoEntry getInfoEntry(Long infoEntryId) {
        Optional<InfoEntry> optionalInfoEntry = infoEntryRepository.findById(infoEntryId);
        return optionalInfoEntry.orElse(null);
    }
}
