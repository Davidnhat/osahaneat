package com.daviaNhat.osahaneat.service;

import com.daviaNhat.osahaneat.dto.CategoryDTO;
import com.daviaNhat.osahaneat.dto.MenuDTO;
import com.daviaNhat.osahaneat.entity.Category;
import com.daviaNhat.osahaneat.entity.Food;
import com.daviaNhat.osahaneat.repository.CategoryReposiroty;
import com.daviaNhat.osahaneat.service.imp.CategoryServiceImp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements CategoryServiceImp {

    @Autowired
    CategoryReposiroty categoryReposiroty;

    @Autowired
    RedisTemplate redisTemplate;

    private Gson gson = new Gson();

    @Override
    public List<CategoryDTO> getCategoryHomePage() {

        String dataRedis = (String) redisTemplate.opsForValue().get("category");
        List<CategoryDTO> listCategoryDTOS = new ArrayList<>();

        if(dataRedis == null){
            System.out.println("Chua co data");
            PageRequest pageRequest = PageRequest.of(0,3, Sort.by("id"));
            Page<Category> listCategory = categoryReposiroty.findAll(pageRequest);

            //Lấy thông tin bảng category trong mysql
            for (Category data : listCategory) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setName(data.getNameCate());

                //Lấy thông tin bảng Food có kết nối với bảng Category trong mysql
                List<MenuDTO> menuDTOS = new ArrayList<>();
                for (Food dataFood : data.getListFood()) {
                    MenuDTO menuDTO = new MenuDTO();
                    menuDTO.setTitle(dataFood.getTitle());
                    menuDTO.setFreeShip(dataFood.isFreeShip());
                    menuDTO.setImage(dataFood.getImage());

                    menuDTOS.add(menuDTO);
                }
                categoryDTO.setMenus(menuDTOS);

                listCategoryDTOS.add(categoryDTO);
            }

            String dataJson = gson.toJson(listCategoryDTOS);
            redisTemplate.opsForValue().set("category", dataJson);
        } else {
            Type listType = new TypeToken<List<CategoryDTO>>(){}.getType();
            listCategoryDTOS = gson.fromJson(dataRedis,listType);
            System.out.println("Co data");
        }
        return listCategoryDTOS;
    }
}
