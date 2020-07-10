package com.jiuxin.springcloud.dao;
import com.juxin.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 1:为了把mapper这个DAO交給Spring管理 http://412887952-qq-com.iteye.com/blog/2392672
 *
 * 2:为了不再写mapper映射文件 https://blog.csdn.net/weixin_39666581/article/details/103899495
 *
 * 3:为了给mapper接口 自动根据一个添加@Mapper注解的接口生成一个实现类
 */
@Mapper
public interface PaymentDao {

    public int creat(Payment payment);
    public Payment getPaymentById(@Param("id") Long id);
}
