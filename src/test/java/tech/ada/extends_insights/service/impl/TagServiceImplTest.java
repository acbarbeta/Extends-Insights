package tech.ada.extends_insights.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.Tag;
import tech.ada.extends_insights.domain.models.requests.TagRequest;
import tech.ada.extends_insights.domain.models.requests.UpdateTagRequest;
import tech.ada.extends_insights.repository.TagRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {

    public static  final long TAG_ID = 1L;
    public static final String TAG_TITLE = "tagTitle";

    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private ModelMapper modelMapper;

    private Tag tag;
    private TagRequest tagRequest;
    private Optional<Tag> optionalTag;
    private UpdateTagRequest updateTagRequest;
    private List<Tag> tagList;
    private Publication publication;

    @BeforeEach
    void setUp() {
        tagService = new TagServiceImpl(tagRepository, modelMapper);
        tag = new Tag(TAG_ID, TAG_TITLE, publication);
        optionalTag = Optional.of(tag);
        tagRequest = new TagRequest(TAG_TITLE);
        updateTagRequest = new UpdateTagRequest("newTagTitle");
        tagList = List.of(tag);
        publication = new Publication();
    }

    @DisplayName("Should create a new tag successfully")
    @Test
    void createTag() {
        Mockito.when(modelMapper.map(any(), any())).thenReturn(tag);
        Mockito.when(tagRepository.save(any())).thenReturn(tag);
        Tag createTag = tagService.createTag(tagRequest);
        assertEquals(TAG_TITLE, createTag.getTitle());
    }

    @DisplayName("Should find all tags successfully")
    @Test
    void findAllTags() {
        Mockito.when(tagRepository.findAll()).thenReturn(tagList);
        List<Tag> allTags = tagService.readAllTags();
        assertEquals(tagList, allTags);
    }

    @DisplayName("Should find tags by publication successfully")
    @Test
    void findTagsByPublication() {
        Mockito.when(tagRepository.findByPublication(publication)).thenReturn(tagList);
        List<Tag> tagsByPublication = tagService.readTagsByPublication(publication);
        assertEquals(tagList, tagsByPublication);
    }

    @DisplayName("Should update tag by id successfully")
    @Test
    void updateTag() {
        Mockito.when(tagRepository.findById(TAG_ID)).thenReturn(optionalTag);
        Mockito.when(tagRepository.save(any())).thenReturn(tag);
        Tag updateTag = tagService.updateTag(TAG_ID, updateTagRequest);
        assertEquals(updateTagRequest.getTitle(), updateTag.getTitle());
    }

    @DisplayName("Should delete tag by id successfully")
    @Test
    void deleteTag() {
        Mockito.when(tagRepository.findById(TAG_ID)).thenReturn(optionalTag);
        tagService.deleteTag(TAG_ID);
        Mockito.verify(tagRepository).delete(tag);
    }
}
