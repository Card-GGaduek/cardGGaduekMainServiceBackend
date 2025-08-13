package org.cardGGaduekMainService.codef.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CodefMapper {

    void createConnectedId(@Param("memberId") Long memberId, @Param("connectedId") String connectedId);
    void addAccount(@Param("connectedId") String connectedId, @Param("organizationCode") String organizationCode);

    String getConnectedIdByMemberId(@Param("memberId") Long memberId);
    List<String> getOrganizationCodeByConnectedId(@Param("connectedId") String connectedId);

    String getConnectedId(@Param("memberId") Long memberId);
}
