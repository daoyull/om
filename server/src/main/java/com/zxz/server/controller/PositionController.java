package com.zxz.server.controller;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxz.server.pojo.Position;
import com.zxz.server.pojo.RespBean;
import com.zxz.server.service.IPositionService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
@RestController
@RequestMapping("/system/basic/position")
public class PositionController {

    @Autowired
    private IPositionService positionService;


    @ApiModelProperty("获取全部职位信息")
    @GetMapping("/")
    public List<Position> getAllPositions(){
        return positionService.list();
    }

    @ApiModelProperty("添加职位信息")
    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position){



        position.setCreateDate(LocalDateTime.now());
        position.setEnabled(true);
        System.out.println(position);
        if(positionService.save(position)){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiModelProperty("更新职位信息")
    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position){
        if(positionService.updateById(position)){
            return RespBean.success("更新成功");
        }
        return RespBean.success("更新失败");
    }

    @ApiModelProperty("删除职位信息")
    @DeleteMapping("/{id}")
    public RespBean deletePosition(@PathVariable Integer id){
        if(positionService.removeById(id)){
            return RespBean.success("删除成功");
        }
        return RespBean.success("删除失败");
    }

    @ApiModelProperty("批量删除")
    @DeleteMapping("/")
    public RespBean deletePositionsByIds(Integer[] ids){
        if(positionService.removeByIds(Arrays.asList(ids))){
            return RespBean.success("删除成功");
        }
        return RespBean.success("删除失败");
    }

}
