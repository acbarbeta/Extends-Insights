package tech.ada.extends_insights.service;

import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    public Tag createTag(Tag tag);

    List<Tag> readAllTags();

    Optional<Tag> readTagById(Long id);

    List<Tag> readTagsByPublication(Publication publication);

    Tag updateTag(Long id, Tag tag);

    void deleteTag(Long id);
}
