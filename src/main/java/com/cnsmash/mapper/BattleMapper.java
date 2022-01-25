package com.cnsmash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.pojo.entity.Battle;
import com.cnsmash.pojo.ro.PageBattleRo;
import com.cnsmash.pojo.vo.PageBattleVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author guanhuan_li
 */
public interface BattleMapper extends BaseMapper<Battle> {

    /**
     * 分页查询
     * @param page 分页数据
     * @param ro 请求
     * @return 结果
     */
    Page<PageBattleVo> page(Page<Battle> page, @Param("ro") PageBattleRo ro);

}