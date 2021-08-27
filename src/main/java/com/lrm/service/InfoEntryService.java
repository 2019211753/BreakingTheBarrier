package com.lrm.service;

import com.lrm.po.InfoEntry;
import org.springframework.data.domain.Page;

public interface InfoEntryService {
    InfoEntry saveInfoEntry(InfoEntry infoEntry);

    Page<InfoEntry> getAllUnapproved();

    Page<InfoEntry> getApprovedByTime();

    InfoEntry approve(Long entryId);

    InfoEntry update(InfoEntry infoEntry);

    InfoEntry getInfoEntry(Long entryId);
}
