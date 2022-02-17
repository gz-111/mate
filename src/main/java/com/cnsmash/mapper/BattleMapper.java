package com.cnsmash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.pojo.entity.Battle;
import com.cnsmash.pojo.ro.PageBattleRo;
import com.cnsmash.pojo.vo.PageBattleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author guanhuan_li
 */
@Repository
public interface BattleMapper extends BaseMapper<Battle> {

    /**
     * 分页查询
     * @param page 分页数据
     * @param ro 请求
     * @return 结果
     */
    Page<PageBattleVo> page(Page<Battle> page, @Param("ro") PageBattleRo ro);

    /**
     * 获取用户正在进行的对战
     * @param userId 用户id
     * @return 对战
     */
    Battle getCurrentBattle(@Param("userId") Long userId);

    /**
     * 获取用户未能正常结束的对战数量
     * @param userId 用户id
     * @return 对战
     */
    Long getConflictBattle(@Param("userId") Long userId);

    /**
     * 计算两人对战次数
     * @param userId1
     * @param userId2
     * @return
     */
    Long getHead2HeadCount(@Param("userId1") Long userId1, @Param("userId2") Long userId2, @Param("quarter") String quarter);
}
