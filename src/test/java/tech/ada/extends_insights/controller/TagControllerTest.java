package tech.ada.extends_insights.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.ada.extends_insights.domain.entities.Tag;
import tech.ada.extends_insights.domain.models.requests.TagRequest;
import tech.ada.extends_insights.service.impl.TagServiceImpl;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.ada.extends_insights.controller.UserControllerTest.asJsonString;

@ExtendWith(MockitoExtension.class)
class TagControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TagServiceImpl tagService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TagController tagController;

    private Tag tag;
    private TagRequest tagRequest;
    private List<Tag> tagList;

    @BeforeEach
    public void setup() {
        //Arrange
        tag = new Tag("tagTitle");
        tagRequest = new TagRequest("tagTitle");
        tagList = List.of(tag);
        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
    }

    @Test
    public void createTagHttpTest() throws Exception {
        when(tagService.createTag(tagRequest)).thenReturn(tag);
        var response = mockMvc.perform(MockMvcRequestBuilders.post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tag)))
                .andExpect(status().isCreated());
        response.andExpect(jsonPath("$.title").value(tag.getTitle()));
        //verify(tagService, times(1)).createTag(any());
    }

    @Test
    void getAllTags() {
    }

    @Test
    void getTagsByPublication() {
    }

    @Test
    void updateTag() {
    }

    @Test
    void deleteTag() {
    }
}