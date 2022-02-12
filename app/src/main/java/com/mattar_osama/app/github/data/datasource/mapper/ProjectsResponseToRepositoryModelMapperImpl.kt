package com.mattar_osama.app.github.data.datasource.mapper

import com.mattar_osama.app.github.data.dto.githubrepositorydto.ProjectsResponseDto
import com.mattar_osama.app.github.data.repository.model.ProjectRepositoryModel
import javax.inject.Inject

interface ProjectsResponseToRepositoryModelMapper {
    fun toRepositoryModel(projectsResponseDto: ProjectsResponseDto): List<ProjectRepositoryModel>
}

class ProjectsResponseToRepositoryModelMapperImpl @Inject constructor() :
    ProjectsResponseToRepositoryModelMapper {
    override fun toRepositoryModel(projectsResponseDto: ProjectsResponseDto): List<ProjectRepositoryModel> {
        return projectsResponseDto.items.map { projectModel ->
            ProjectRepositoryModel(
                ownerName = projectModel.owner.login,
                projectName = projectModel.name,
                projectDescription = projectModel.description,
                avatarUrl = projectModel.owner.avatarUrl
            )
        }
    }
}