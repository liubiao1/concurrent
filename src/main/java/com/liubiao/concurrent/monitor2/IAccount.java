package com.liubiao.concurrent.monitor2;

/**
 * 账户
 *
 * @author mc0710
 */
public interface IAccount {

    /**
     * 获取余额
     *
     * @return 余额
     */
    Integer getBalance();

    /**
     * 取款
     *
     * @param amount 取款金额
     */
    void withdraw(Integer amount);

}
