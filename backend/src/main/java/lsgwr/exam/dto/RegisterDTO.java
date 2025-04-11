/***********************************************************
 * @Description : 注册接口参数
 ***********************************************************/
package lsgwr.exam.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String email;
    private String password;
    private String password2;
    private String mobile;
    /**
     * 验证码
     */
    private String captcha;
}
