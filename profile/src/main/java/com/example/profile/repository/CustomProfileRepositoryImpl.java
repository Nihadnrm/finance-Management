package com.example.profile.repository;

import com.example.profile.dto.ProfileResponseDto;
import com.example.profile.entity.Profile;
import com.example.profile.entity.QProfile;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CustomProfileRepositoryImpl implements CustomProfileRepository{

    private JPAQueryFactory queryFactory;

    public CustomProfileRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Profile> searchByName(Pageable pageable, String pName) {
        QProfile profile=QProfile.profile;
        BooleanBuilder builder=new BooleanBuilder();

        builder.and(profile.profileName.contains(pName));

        List<Profile>list=queryFactory
                .selectFrom(profile)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total=queryFactory
                .select(profile.count())
                .from(profile)
                .where(builder)
                .fetchOne();
        return new PageImpl<>(list,pageable,total);
    }
}
