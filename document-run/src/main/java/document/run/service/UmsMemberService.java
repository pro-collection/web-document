package document.run.service;

import document.run.common.api.CommonResult;

public interface UmsMemberService {
    /* 生成验证码 */
    CommonResult generateAuthCode(String phone);

    /* 判断验证码和手机是否匹配 */
    CommonResult verifyAuthCode(String phone, String authCode);
}
