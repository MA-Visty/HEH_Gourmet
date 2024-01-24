package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.application.port.in.IManageFavUseCase;
import be.heh.gourmet.application.port.in.exception.FavException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FavAdapterUseCase implements IManageFavUseCase {
    private JdbcTemplate jdbc;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public void add(int userID, int productID) {
        try {
            if (list(userID).contains(productID)) {
                throw new FavException("Error while placing fav", FavException.Type.FAV_ALREADY_EXIST);
            }
            jdbc.update("INSERT INTO favorites (user_id, product_id) VALUES (?,?)", userID, productID);
        } catch (Exception e) {
            throw new FavException("Error while placing fav", FavException.Type.FAV_NOT_CREATED, e);
        }
    }

    @Override
    public void remove(int userID, int productID) {
        try {
            if (!list(userID).contains(productID)) {
                throw new FavException("Error while placing fav", FavException.Type.FAV_NOT_FOUND);
            }
            jdbc.update("DELETE FROM favorites WHERE user_id = ? AND product_id = ?", userID, productID);
        } catch (Exception e) {
            throw new FavException("Error while removing fav", FavException.Type.FAV_NOT_DELETED, e);
        }
    }

    @Override
    public List<Integer> list(int userID) {
        try {
            return jdbc.queryForList("SELECT product_id FROM favorites WHERE user_id = ?", Integer.class, userID);
        } catch (Exception e) {
            throw new FavException("Error while listing fav", FavException.Type.FAV_NOT_FOUND, e);
        }
    }
}
