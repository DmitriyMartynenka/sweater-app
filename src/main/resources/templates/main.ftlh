<#import "parts/common.ftl" as c>

<@c.page>
    <div class="input-group">
        <form method="get" action="/main">
            <input type="text" name="filter" value="${filter!""}" placeholder="Search by tag">
            <button class="btn btn-primary ml-2" type="submit">Search</button>
        </form>
    </div>
    <a class="btn btn-primary mt-2" data-bs-toggle="collapse" href="#collapseExample" role="button"
       aria-expanded="false" aria-controls="collapseExample">
        Add new Message
    </a>
    <div class="collapse <#if message??>show</#if>" id="collapseExample">
        <div>
            <form method="post" enctype="multipart/form-data">
                <div class="mt-1">
                    <input type="text" class="form-control ${(textError??)?string('is-invalid', '')}" name="text" value="<#if message??>${message.text}</#if>"  placeholder="Введите сообщение"/>
                    <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                    </#if>
                </div>
                <div class="mt-2">
                    <input type="text" class="form-control ${(tagError??)?string('is-invalid', '')}" name="tag" value="<#if message??>${message.tag}</#if>" placeholder="Тэг">
                    <#if tagError??>
                        <div class="invalid-feedback">
                            ${tagError}
                        </div>
                    </#if>
                </div>
                <div class="mt-2">
                    <div class="custom-file">
                        <input type="file" name="file" id="customFile">
                        <label class="custom-file-label" for="customFile"></label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="mt-2">
                    <button type="submit" class="btn btn-primary">Добавить</button>
                </div>
            </form>
        </div>
    </div>

    <div class="row row-cols-4 row-cols-mt-3">
        <#list messages as message>
            <div class="card m-2">
                <div>
                    <#if message.filename??>
                        <img src="/img/${message.filename}" class="card-img-top">
                    </#if>
                </div>
                <div class="m-2">
                    <span>${message.text}</span>
                    <i>${message.tag}</i>
                </div>
                <div class="card-footer text-muted">
                    ${message.authorName}
                </div>
            </div>
        <#else>
            No message
        </#list>
    </div>
</@c.page>
