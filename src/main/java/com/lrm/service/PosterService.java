package com.lrm.service;

import com.lrm.po.InfoEntry;
import com.lrm.po.Poster;
import org.springframework.data.domain.Page;

public interface PosterService {
    Poster savePoster(Poster infoEntry);

    void deletePoster(Long posterId);

    Page<Poster> searchPoster(int pageIndex, String query);

    Poster update(Poster newPoster);

    Poster getPoster(Long poserId);
}
