<a class="btn btn-primary mt-2" data-bs-toggle="collapse" href="#collapseExample" role="button"
   aria-expanded="false" aria-controls="collapseExample">
    Message editor
</a>
<div class="collapse <#if message??>show</#if>" id="collapseExample">
    <div>
        <form method="post" enctype="multipart/form-data">
            <div class="mt-1">
                <input type="text" class="form-control ${(textError??)?string('is-invalid', '')}" name="text"
                       value="<#if message??>${message.text}</#if>" placeholder="Введите сообщение"/>
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="mt-2">
                <input type="text" class="form-control ${(tagError??)?string('is-invalid', '')}" name="tag"
                       value="<#if message??>${message.tag}</#if>" placeholder="Тэг"/>
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
            </div>
            <div class="mt-2">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile"/>
                    <label class="custom-file-label" for="customFile"></label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="messageId" value="<#if message??>${message.id!''}</#if>"/>
            <div class="mt-2">
                <button type="submit" class="btn btn-primary">Save message</button>
            </div>
        </form>
    </div>
</div>