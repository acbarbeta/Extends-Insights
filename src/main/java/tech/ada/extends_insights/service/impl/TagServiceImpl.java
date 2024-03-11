package tech.ada.extends_insights.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.Tag;
import tech.ada.extends_insights.domain.models.requests.TagRequest;
import tech.ada.extends_insights.domain.models.requests.UpdateTagRequest;
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

    @Override
    public Tag createTag(TagRequest tagRequest){
        Tag convertedTag = modelMapper.map(tagRequest, Tag.class);
        return tagRepository.save(convertedTag);
    }

    public List<Tag> readAllTags(){
        return tagRepository.findAll();
    }

    public Optional<Tag> readTagById(Long id){
        Optional<Tag> optionalTag = tagRepository.findById(id);
        return optionalTag;
    }

    public List<Tag> readTagsByPublication(Publication publication){
        return tagRepository.findByPublication(publication);
    }

    public Tag updateTag(Long id, UpdateTagRequest updateTagRequest){
        Tag tagToUpdate = tagRepository.findById(id).orElse(null);
        if(tagToUpdate != null){
            tagToUpdate.setTitle(updateTagRequest.getTitle());
            tagRepository.save(tagToUpdate);
        }
        return tagToUpdate;
    }

    public void deleteTag(Long id){
        Optional<Tag> optionalTag = tagRepository.findById(id);

        if (optionalTag.isPresent()) {
            tagRepository.delete(optionalTag.get());
            ResponseEntity.noContent().build();
        } else {
            ResponseEntity.notFound().build();
        }
    }
}
