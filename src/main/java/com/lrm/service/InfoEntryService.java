package com.lrm.service;

import com.lrm.po.InfoEntry;

public interface InfoEntryService {
    InfoEntry saveInfoEntry(InfoEntry infoEntry);

    InfoEntry update(InfoEntry infoEntry);

    InfoEntry getInfoEntry(Long infoEntryId);
}
