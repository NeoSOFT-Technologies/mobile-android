package com.arch.usecase

import com.arch.entity.ResourceData
import com.arch.repository.ResourceRepository
import com.arch.usecase.base.BaseUseCase
import com.arch.usecase.base.Params
import com.arch.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetResourceDetailsUseCase @Inject constructor(
    private val resourceRepository: ResourceRepository,
) : BaseUseCase<GetResourceDetailsUseCase.ResourceParams, Flow<Resource<ResourceData?>>> {

    override suspend fun execute(params: ResourceParams): Flow<Resource<ResourceData?>> {
        return resourceRepository.getResourceDetails(params.id)
    }

    class ResourceParams(
        val id: Int
    ) : Params {
        override fun verify(): Boolean {
            // Write your validations here
            return true
        }

    }

}
