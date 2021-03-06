package net.maku.college.convert;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import net.maku.college.vo.EnterpriseResultVo;
import net.maku.enterprise.entity.SysOrgDetailsEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-15T21:38:26+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_332 (Amazon.com Inc.)"
)
public class SysEnterpriseConvertImpl implements SysEnterpriseConvert {

    @Override
    public List<EnterpriseResultVo> convertList(List<SysOrgDetailsEntity> sysUserEntityList) {
        if ( sysUserEntityList == null ) {
            return null;
        }

        List<EnterpriseResultVo> list = new ArrayList<EnterpriseResultVo>( sysUserEntityList.size() );
        for ( SysOrgDetailsEntity sysOrgDetailsEntity : sysUserEntityList ) {
            list.add( sysOrgDetailsEntityToEnterpriseResultVo( sysOrgDetailsEntity ) );
        }

        return list;
    }

    protected EnterpriseResultVo sysOrgDetailsEntityToEnterpriseResultVo(SysOrgDetailsEntity sysOrgDetailsEntity) {
        if ( sysOrgDetailsEntity == null ) {
            return null;
        }

        EnterpriseResultVo enterpriseResultVo = new EnterpriseResultVo();

        enterpriseResultVo.setOrgId( sysOrgDetailsEntity.getOrgId() );
        enterpriseResultVo.setName( sysOrgDetailsEntity.getName() );
        enterpriseResultVo.setIntro( sysOrgDetailsEntity.getIntro() );
        enterpriseResultVo.setAddress( sysOrgDetailsEntity.getAddress() );
        enterpriseResultVo.setType( sysOrgDetailsEntity.getType() );

        return enterpriseResultVo;
    }
}
