package be.heh.gourmet.adapter.in.web;

import be.heh.gourmet.adapter.out.persistence.CartRepository;
import be.heh.gourmet.application.domain.model.CartRow;
import be.heh.gourmet.application.domain.model.Product;
import be.heh.gourmet.application.port.in.IManageCartUseCase;
import be.heh.gourmet.application.port.in.IManageProductUseCase;
import be.heh.gourmet.application.port.in.IPaymentUseCase;
import be.heh.gourmet.application.port.in.exception.CartException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("getManageCartUseCase")
    private IManageCartUseCase cartManager;

    @MockBean
    @Qualifier("getManageProductUseCase")
    private IManageProductUseCase productManager;

    @MockBean
    @Qualifier("getPaymentUseCase")
    private IPaymentUseCase paymentManager;

    @MockBean
    private CartRepository cartRepository;

    private final CartRow cartRow1 = new CartRow(1, "Item 1", 1, 1, 1);
    private final CartRow cartRow2 = new CartRow(2, "Item 2", 2, 2, 2);
    private final List<CartRow> cart = List.of(cartRow1, cartRow2);

    @Test
    void getCart() throws Exception, CartException {
        when(cartManager.get(1)).thenReturn(cart);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cart/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ID").value(cartRow1.ID()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(cartRow1.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantity").value(cartRow1.quantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(cartRow1.price()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalPrice").value(cartRow1.totalPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].ID").value(cartRow2.ID()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(cartRow2.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].quantity").value(cartRow2.quantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price").value(cartRow2.price()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].totalPrice").value(cartRow2.totalPrice()));

    }

    @Test
    void getCartNotFound() throws Exception, CartException {
        when(cartManager.get(1)).thenThrow(new CartException("Cart not found", CartException.Type.ASSOCIATED_USER_NOT_FOUND));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cart/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addToCart() throws Exception, CartException {
        when(productManager.get(1)).thenReturn(Optional.of(new Product(1, "Item 1", "desc", 1, 1, new URL("http://localhost"), 1)));
        doNothing().when(cartRepository).add(1, 1, 1);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cart/1?quantity=1&productID=1"))
                .andExpect(status().isCreated());
    }

    @Test
    void addToCartWithInvalidQuantity() throws Exception, CartException {
        when(productManager.get(1)).thenReturn(Optional.of(new Product(1, "Item 1", "desc", 1, 1, new URL("http://localhost"), 1)));
        doNothing().when(cartRepository).add(1, 1, 1);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cart/1?quantity=-1&productID=1"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void removeFromCart() throws Exception {
        doNothing().when(cartRepository).remove(1, 1);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cart/1?quantity=1&productID=1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void checkout() throws Exception, CartException {
        // targetDate = now + 1 year
        Date targetDate = new Date(System.currentTimeMillis() + 31536000000L);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String targetDateStr = formatter.format(targetDate);
        when(cartManager.get(1)).thenReturn(cart);
        doNothing().when(cartManager).placeOrder(1, targetDate);
        // TODO: Mock the behavior of paymentManager.charge() when implemented

        Map<String, Object> params = new HashMap<>();
        params.put("targetDate", targetDateStr);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cart/1/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isOk());
    }

    @Test
    void checkoutInvalidDate() throws Exception, CartException {
        // prepare data
        // targetDate = now + 1 year
        Date targetDate = new Date(System.currentTimeMillis() + 31536000000L);
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        String targetDateStr = formatter.format(targetDate);

        Map<String, Object> params = new HashMap<>();
        params.put("targetDate", targetDateStr);

        // mock behavior
        when(cartManager.get(1)).thenReturn(cart);
        doNothing().when(cartManager).placeOrder(1, targetDate);
        doNothing().when(paymentManager).charge(1, cart, Map.of("targetDate", targetDateStr));


        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cart/1/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void checkoutEmptyCart() throws Exception, CartException {
        // prepare data
        // targetDate = now + 1 year
        Date targetDate = new Date(System.currentTimeMillis() + 31536000000L);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String targetDateStr = formatter.format(targetDate);

        Map<String, Object> params = new HashMap<>();
        params.put("targetDate", targetDateStr);

        // mock behavior
        when(cartManager.get(1)).thenReturn(List.of());
        doNothing().when(cartManager).placeOrder(1, targetDate);
        doNothing().when(paymentManager).charge(1, List.of(), Map.of("targetDate", targetDateStr));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cart/1/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isConflict());
    }

    @Test
    void checkoutMissingDate() throws Exception, CartException {
        when(cartManager.get(1)).thenReturn(cart);
        doNothing().when(paymentManager).charge(1, cart, Map.of());

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cart/1/checkout"))
                .andExpect(status().isBadRequest());
    }
}