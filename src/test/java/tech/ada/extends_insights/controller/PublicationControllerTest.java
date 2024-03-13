package tech.ada.extends_insights.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.Tag;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.enums.Category;
import tech.ada.extends_insights.domain.models.requests.PublicationRequest;
import tech.ada.extends_insights.domain.models.requests.UpdatePublicationRequest;
import tech.ada.extends_insights.service.impl.PublicationServiceImpl;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PublicationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PublicationServiceImpl publicationService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PublicationController publicationController;

    private Publication publication;
    private List<Publication> publicationList;
    private PublicationRequest publicationRequest;
    private UpdatePublicationRequest updatePublicationRequest;

    private User user;
    private Category category;
    private Tag tag;
    private List<Tag> tagList;

    @BeforeEach
    void setUp() {
        user = new User("usernameTest", "123456789", "email@test.com");
        publication = new Publication("title", "content", user, category, tagList);
        publicationList = List.of(publication);
        category = Category.TECHNOLOGY;
        tag = new Tag("tagTitle");
        mockMvc = MockMvcBuilders.standaloneSetup(publicationController).build();
    }

    @Test
    void createPublication() throws Exception {
        when(publicationService.createPublication(any())).thenReturn(publication);
        mockMvc.perform(MockMvcRequestBuilders.post("/publications-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publication)))
                .andExpect(status().isCreated());
        verify(publicationService, times(1)).createPublication(any());
    }

    @Test
    void getAllPublications() throws Exception {
        when(publicationService.readAllPublications()).thenReturn(publicationList);
        mockMvc.perform(MockMvcRequestBuilders.get("/publications-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publication)))
                .andDo(MockMvcResultHandlers.print());
        verify(publicationService).readAllPublications();
        verify(publicationService, times(1)).readAllPublications();
    }

    @Test
    void getPublicationById() throws Exception {
        when(publicationService.readPublicationById(anyLong())).thenReturn(publication);
        mockMvc.perform(MockMvcRequestBuilders.get("/publications-items/" + anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publication)))
                .andDo(MockMvcResultHandlers.print());
        verify(publicationService).readPublicationById(anyLong());
        verify(publicationService, times(1)).readPublicationById(anyLong());
    }

    @Test
    void getPublicationByTitle() throws Exception {
        when(publicationService.readPublicationByTitle(anyString())).thenReturn(publicationList);
        mockMvc.perform(MockMvcRequestBuilders.get("/publications-items?title=" + anyString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publication)))
                .andDo(MockMvcResultHandlers.print());
        verify(publicationService).readPublicationByTitle(anyString());
        verify(publicationService, times(1)).readPublicationByTitle(anyString());
    }

    @Test
    void getPublicationByCategory() throws Exception {
        when(publicationService.getPublicationByCategory(category)).thenReturn(publicationList);
        mockMvc.perform(MockMvcRequestBuilders.get("/publications-items?category=" + category)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publication)))
                .andDo(MockMvcResultHandlers.print());
        verify(publicationService).getPublicationByCategory(category);
        verify(publicationService, times(1)).getPublicationByCategory(category);
    }

    @Test
    void getPublicationByTag() throws Exception {
        when(publicationService.getPublicationByTag(any())).thenReturn(publicationList);
        mockMvc.perform(MockMvcRequestBuilders.get("/publications-items?tag=" + any())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publication)))
                .andDo(MockMvcResultHandlers.print());
        verify(publicationService).getPublicationByTag(any());
        verify(publicationService, times(1)).getPublicationByTag(any());
    }

    @Test
    void getPublicationByUser() throws Exception {
        when(publicationService.getPublicationByUser(user)).thenReturn(publicationList);
        mockMvc.perform(MockMvcRequestBuilders.get("/publications-items?author=" + user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publication)))
                .andDo(MockMvcResultHandlers.print());
        verify(publicationService).getPublicationByUser(user);
        verify(publicationService, times(1)).getPublicationByUser(user);
    }

    @Test
    void updatePublication() throws Exception {
        when(publicationService.updatePublication(anyLong(), any())).thenReturn(publication);
        mockMvc.perform(MockMvcRequestBuilders.patch("/publications-items/{id}", anyLong())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatePublicationRequest)))
                .andExpect(status().isOk());
        verify(publicationService, times(1)).updatePublication(anyLong(), any());
    }

    @Test
    public void deletePublicationHttpTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/publications-items/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publication)))
                .andExpect(status().isNoContent());
        verify(publicationService, times(1)).deletePublication(anyLong());
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static String asJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}