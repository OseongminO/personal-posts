package study.nobreak.personalposts.so.web

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import study.nobreak.personalposts.so.service.SoPostService
import study.nobreak.personalposts.so.web.request.SoHiddenContentCreateRequest
import study.nobreak.personalposts.so.web.request.SoPostCreateRequest
import study.nobreak.personalposts.so.web.response.SoPostGetResponse
import study.nobreak.personalposts.so.web.response.SoPostResponseItem

@RestController
@RequestMapping("/so/posts")
class SoPostController(
    private val soPostService: SoPostService
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getPosts(): SoPostGetResponse {
        return SoPostGetResponse(soPostService.getAllPosts().map { SoPostResponseItem.fromSoPost(it) })
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addPost(@RequestBody soPostCreateRequest: SoPostCreateRequest) {
        soPostService.addPost(soPostCreateRequest.title, soPostCreateRequest.content)
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePost(@PathVariable id: Long) {
        return soPostService.deletePost(id)
    }
    
    @PostMapping("/hidden")
    @ResponseStatus(HttpStatus.CREATED)
    fun addHiddenContent(@RequestBody soHiddenContentCreateRequest: SoHiddenContentCreateRequest) {
        with(soHiddenContentCreateRequest) {
            soPostService.addHiddenContent(postId, question, answer, content)
        }
    }
}
