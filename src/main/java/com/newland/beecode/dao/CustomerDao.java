package com.newland.beecode.dao;

import org.springframework.stereotype.Repository;

import com.newland.beecode.domain.Customer;

/**
 * @author shaoxr:
 * @version 2011-5-12 下午08:00:50
 * 
 */
@Repository("customerDao")
public class CustomerDao extends BaseDao<Customer,Long>{

}
