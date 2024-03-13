package tech.ada.extends_insights.service;

import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.Tag;
import tech.ada.extends_insights.domain.models.requests.TagRequest;
import tech.ada.extends_insights.domain.models.requests.UpdateTagRequest;

import java.util.List;
import java.util.Optional;

public interface TagService {
    Tag createTag(TagRequest tagRequest);

    List<Tag> readAllTags();

    Optional<Tag> readTagById(Long id);

    List<Tag> readTagsByPublication(Publication publication);

    Tag updateTag(Long id, UpdateTagRequest updateTagRequest);

    void deleteTag(Long id);
}
