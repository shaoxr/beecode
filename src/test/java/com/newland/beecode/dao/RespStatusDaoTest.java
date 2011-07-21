package com.newland.beecode.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests; 

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import com.newland.beecode.domain.RespStatus;
import com.newland.beecode.task.SendParam;
import com.newland.beecode.task.Task;
import com.newland.beecode.task.ThreadPoolFactory;

@ContextConfiguration(locations = "/applicationContext.xml") 

public class RespStatusDaoTest extends AbstractJUnit4SpringContextTests {
	
	@Resource(name = "respStatusDao")
	private RespStatusDao respStatusDao;
	
	@Test
	public void testFindByProperty(){
		for(int i=0;i<10;i++){
			final SendParam sp=new SendParam();
			sp.setMobile("1"+i);
			sp.setCouponId(new Long(1+""+i));
			final ThreadPoolExecutor threadPool =ThreadPoolFactory.newThreadPool(10);
			threadPool.execute(new aaa(sp));
			
			
		}
		try {
			Thread.sleep(21000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run(SendParam sp){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sp.getMobile());
	}
	public class aaa implements Runnable{
		private SendParam sp;
        public aaa(SendParam sp){
        	this.sp=sp;
        }
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(sp.getMobile()+":"+sp.getCouponId());
			
		}
		
	}

}
