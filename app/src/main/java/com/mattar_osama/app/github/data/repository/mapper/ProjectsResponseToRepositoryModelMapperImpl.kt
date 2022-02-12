package com.mattar_osama.app.github.data.repository.mapper

import com.mattar_osama.app.github.data.repository.model.ProjectRepositoryModel
import com.mattar_osama.app.github.domain.model.ProjectDomainModel
import javax.inject.Inject

interface ProjectsRepositoryToDomainModelMapper {
    fun toDomainModel(projectsRepositoryModel: List<ProjectRepositoryModel>): List<ProjectDomainModel>
}

class ProjectsRepositoryToDomainModelMapperImpl @Inject constructor() :
    ProjectsRepositoryToDomainModelMapper {
    override fun toDomainModel(projectsRepositoryModel: List<ProjectRepositoryModel>): List<ProjectDomainModel> {
        return projectsRepositoryModel.map { projectModel ->
            ProjectDomainModel(
                projectModel.ownerName,
                projectModel.projectName,
                projectModel.projectDescription,
                projectModel.avatarUrl
            )
        }
    }
}