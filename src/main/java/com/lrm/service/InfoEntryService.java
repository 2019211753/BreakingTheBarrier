package com.lrm.service;

import com.lrm.po.InfoEntry;
import org.springframework.data.domain.Page;

public interface InfoEntryService {
    InfoEntry saveInfoEntry(InfoEntry infoEntry, String[] tagNames);

    void deleteEntry(Long entryId);

    Page<InfoEntry> searchEntry(int pageIndex, String query);

    Page<InfoEntry> getUnapproved(int pageIndex);

    Page<InfoEntry> getApprovedByTime(int pageIndex);

    InfoEntry approve(Long entryId);

    InfoEntry disapprove(Long entryId);

    InfoEntry update(InfoEntry newEntry, String[] tagNames);

    InfoEntry getInfoEntry(Long entryId);

    InfoEntry searchEntryById(Long id);
}
