package com.zxz.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_employee_remove")
@ApiModel(value="EmployeeRemove对象", description="")
public class EmployeeRemove implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "员工id")
    private Integer eid;
    @ApiModelProperty(value = "员工名")
    @TableField(exist = false)
    private String ename;

    @ApiModelProperty(value = "调动后部门ID")
    private Integer afterDepId;

    @ApiModelProperty(value = "调动后职位ID")
    private Integer afterJobId;

    @ApiModelProperty(value = "调动后职称ID")
    private Integer afterPosId;

    @ApiModelProperty(value = "调动日期")
    private LocalDate removeDate;

    @ApiModelProperty(value = "调动原因")
    private String reason;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "调动后部门")
    @TableField(exist = false)
    private String depName;

    @ApiModelProperty(value = "调动后职位")
    @TableField(exist = false)
    private String jobName;

    @ApiModelProperty(value = "调动后职称")
    @TableField(exist = false)
    private String posName;






}
