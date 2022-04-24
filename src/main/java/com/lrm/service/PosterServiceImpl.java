package com.lrm.service;

import com.lrm.dao.PosterRepository;
import com.lrm.dao.TemplateRepository;
import com.lrm.exception.FailedOperationException;
import com.lrm.exception.NotFoundException;
import com.lrm.po.Blog;
import com.lrm.po.InfoEntry;
import com.lrm.po.Poster;
import com.lrm.po.User;
import com.lrm.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PosterServiceImpl implements PosterService{
    @Autowired
    private PosterRepository posterRepository;

    @Override
    public Poster savePoster(Poster poster) {
        // aop切面记得改一下
        poster.setTeamLeader(UserHolder.getLocalUser());
        return posterRepository.save(poster);
    }

    @Override
    public void deletePoster(Long posterId) {
        Optional<Poster> poster = posterRepository.findById(posterId);
        if (!poster.isPresent())
            throw new NotFoundException("未发现指定词条");
        posterRepository.delete(poster.get());
    }

    @Override
    public Page<Poster> searchPoster(int pageIndex, String query) {
        if (query == null) return null;
        return posterRepository.findByTitle(query, PageRequest.of(pageIndex, 30));
    }

    @Override
    public Poster update(Poster newPoster) {
        Optional<Poster> found = posterRepository.findById(newPoster.getId());
        if (!found.isPresent()) {
            throw new NotFoundException("此词条不存在");
        }
        Poster poster = found.get();
        poster.setNewContent(newPoster.getNewContent());
        poster.setDiscription(newPoster.getDiscription());
        return poster;
    }

    @Override
    public Poster getPoster(Long posterId) {
        Optional<Poster> optionalPoster = posterRepository.findById(posterId);
        return optionalPoster.orElse(null);
    }
}
