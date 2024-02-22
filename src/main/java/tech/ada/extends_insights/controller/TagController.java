package tech.ada.extends_insights.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.Tag;
import tech.ada.extends_insights.domain.models.requests.TagRequest;
import tech.ada.extends_insights.domain.models.requests.UpdateTagRequest;
import tech.ada.extends_insights.repository.TagRepository;

import java.util.List;
import java.util.Optional;

@RestController("/tags")
public class TagController {
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TagController(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/tags-creation")
    public ResponseEntity<Tag> createTag(@RequestBody TagRequest request) {

        Tag convertedTag = modelMapper.map(request, Tag.class);

        Tag newTag = tagRepository.save(convertedTag);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTag);
    }

    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> allTags = tagRepository.findAll();
        return ResponseEntity.ok(allTags);
    }

    @GetMapping(value = "/tags", params = {"publicationId"})
    public ResponseEntity<List<Tag>> getTagsByPublication(@RequestParam Publication publication) {
        List<Tag> tagsByPublication = tagRepository.findByPublication(publication);
        if(tagsByPublication == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tagsByPublication);
    }

    @PatchMapping("/tags/{id}")
    public ResponseEntity<Tag> updateTag(
            @PathVariable Long id,
            @RequestBody UpdateTagRequest request) throws Exception {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        if(optionalTag.isPresent()) {
            Tag tag = optionalTag.get();
            tag.setTitle(request.getTitle());
            Tag updateTag = tagRepository.save(tag);
            return ResponseEntity.ok(updateTag);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
