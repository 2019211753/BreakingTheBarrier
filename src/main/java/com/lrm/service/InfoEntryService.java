package com.lrm.service;

import com.lrm.po.InfoEntry;
import org.springframework.data.domain.Page;

public interface InfoEntryService {
    InfoEntry saveInfoEntry(InfoEntry infoEntry);

    void deleteEntry(Long entryId);

    Page<InfoEntry> searchEntry(String query);

    Page<InfoEntry> getAllUnapproved();

    Page<InfoEntry> getApprovedByTime();

    InfoEntry approve(Long entryId);

    InfoEntry disapprove(Long entryId);

    InfoEntry update(InfoEntry infoEntry);

    InfoEntry getInfoEntry(Long entryId);
}
