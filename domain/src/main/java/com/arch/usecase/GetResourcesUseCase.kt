package com.arch.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.arch.entity.ResourceData
import com.arch.error.BaseError
import com.arch.repository.ResourceRepository
import com.arch.usecase.base.BaseUseCase
import com.arch.usecase.base.Params
import com.arch.utils.Either
import javax.inject.Inject


class GetResourcesUseCase @Inject constructor(
    private val resourceRepository: ResourceRepository,
) : BaseUseCase<GetResourcesUseCase.ResourcesParams, Either<BaseError, Pager<Int, ResourceData>>> {

    override suspend fun execute(params: ResourcesParams): Either<BaseError, Pager<Int, ResourceData>> {
        return resourceRepository.getResourceData(params.pagingConfig)
    }

    class ResourcesParams(
        val pagingConfig: PagingConfig
    ) : Params {
        override fun verify(): Boolean {
            // Write your validations here
            return true
        }

    }

}
