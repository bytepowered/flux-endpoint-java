package net.bytepowered.flux.samples.entity;

import lombok.Data;
import net.bytepowered.flux.annotation.FxForm;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 陈哈哈 (yongjia.chen@hotmail.com)
 */
@Data
public class UserDO implements Serializable {

    @FxForm
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotNull
    private Integer age;

    private Long id;
}
