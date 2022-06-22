package com.demo.practicleapp.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import java.util.List;

@Dao
public interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<Post> posts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Post post);

    @Delete
    void delete(Post post);

    @Query("SELECT * FROM item_posts_db")
    LiveData<List<Post>> findAll();

    @Query("SELECT * FROM item_posts_db WHERE item_is_stock LIKE '%' || :query || '%' ")
    LiveData<List<Post>> findSearchValue(String query);

}
