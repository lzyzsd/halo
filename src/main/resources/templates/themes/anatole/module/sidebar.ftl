<div class="sidebar animated fadeInDown">
    <div class="logo-title">
        <div class="title">
            <img src="${options.blog_logo!'${static!}/source/images/logo@2x.png'}" style="width:127px;<#if settings.avatar_circle!false>border-radius:50%</#if>" />
            <h3 title="">
                <a href="${context!}">${options.blog_title!}</a>
            </h3>
            <div class="description">
                <#if settings.hitokoto!false>
                    <p id="yiyan">获取中...</p>
                <#else >
                    <p>${user.description!}</p>
                </#if>
            </div>
        </div>
    </div>
    <#include "social-list.ftl">
    <div class="footer">
        <a target="_blank" href="#">
            <#-- 不允许修改该主题信息，也不能删除。 -->
            <a href="https://beian.miit.gov.cn/"><span>网站备案：沪ICP备20003233号-1</span></a>
            <div class="by_halo">
                <a href="https://beian.miit.gov.cn/"></a>
            </div>
            <div class="footer_text">
                <@global.footer_info />
            </div>
        </a>
    </div>
</div>
