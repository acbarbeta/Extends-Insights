package tech.ada.extends_insights.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.Tag;
import tech.ada.extends_insights.repository.TagRepository;
import tech.ada.extends_insights.service.TagService;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public TagServiceImpl(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = new ModelMapper();
    }

    public Tag createTag(Tag tag){
        tagRepository.save(tag);
        return tag;
    }

    public List<Tag> readAllTags(){
        return tagRepository.findAll();
    }

    public Optional<Tag> readTagById(Long id){
        return tagRepository.findById(id);
    }
    public List<Tag> readTagsByPublication(Publication publication){
        return tagRepository.findByPublication(publication);
    }

    public Tag updateTag(Long id, Tag tag){
        Tag tagToUpdate = tagRepository.findById(id).orElse(null);
        if(tagToUpdate != null){
            tagToUpdate.setTitle(tag.getTitle());
            tagRepository.save(tagToUpdate);
        }
        return tagToUpdate;
    }

    public void deleteTag(Long id){
        tagRepository.deleteById(id);
    }
}
