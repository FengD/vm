package hirain.itd.hmi.demo.serviceimpl;

import com.github.pagehelper.Page;
import hirain.itd.hmi.demo.bean.vo.PageBean;
import org.springframework.stereotype.Service;

@Service
public class PageService {
   public  PageBean  getBeanByPage(Page p,int pageCode,int pageSize)
   {
       PageBean pageBean=new PageBean();
       pageBean.setPageCode(pageCode);
       pageBean.setTotalPage((int)Math.ceil((double)(p.getTotal() / (double)pageSize)));
       pageBean.setTotalCount((int)p.getTotal());
       pageBean.setPageSize(pageSize);
       pageBean.setBeanList(p.getResult());
       return pageBean;
   }
}
