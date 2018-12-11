# SpringMVC学习总结

## 1初始化流程
![avatar](DispatcherServlet.png)
>> Servlet的整体继承结构一共有五个类，GenericServlet和HttpServlet是在Java中，HttpServletBean,FrameworkServlet和DispatcherServlet是在SpringMVC中的。
>> XXXAware在spring里表示对XXX的感知：如果某个类里面想要使用spring里面的一些东西，就可以通过事项XXXAware接口，spring看到后就会给你送给过来接受方式通过setXXX方法。
>> EnvironmentCapable，实现此接口代表可以提供Environment，当spring需要Environment的时候就会调用getEnvironment()方法。
### 1.1Servlet
Servlet是一套规范
```java
public interface Servlet {
    /**
     * 容器启动的时候运行
     */
    public void init(ServletConfig config) throws ServletException;

    /**
     * 获取ServletConfig
     */
    public ServletConfig getServletConfig();

    /**
     * 处理具体请求
     */
    public void service(ServletRequest req, ServletResponse res)
            throws ServletException, IOException;

    /**
     * 获取Servlet相关信息（需要自己实现）
     */
    public String getServletInfo();

    /**
     * 销毁时释放资源（只调用一次）
     */
    public void destroy();
}
````

```java
public interface ServletConfig {

    /**
     * 返回servlet的名称，web-xml中定义的servlet-name
     */
    public String getServletName();


    /**
     * 获取ServletContext，代表应用本身
     */
    public ServletContext getServletContext();


    /**
     * 获取init-param配置的参数
     */
    public String getInitParameter(String name);


    /**
     * 获取init-param配置的参数名称
     */
    public Enumeration<String> getInitParameterNames();
}
```
ServletConfig是Servlet级别的，而ServletContext是Context级（Application）的并不是只保存参数。
### 1.2GenericServlet
主要做了三件事：
- 实现ServletConfig接口
- 提供无参init方法
- 提供log方法
``` java
public abstract class GenericServlet implements javax.servlet.Servlet, ServletConfig, Serializable {
    private static final String LSTRING_FILE = "javax.servlet.LocalStrings";
    private static ResourceBundle lStrings =
            ResourceBundle.getBundle(LSTRING_FILE);

    private transient ServletConfig config;

    /**
     * 什么都不干，初始化方法由init方法
     */
    public GenericServlet() {
    }

    public void destroy() {
    }

    /**
     * 获取init-param配置的参数,调用ServletConfig里的方法
     */
    public String getInitParameter(String name) {
        ServletConfig sc = getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(
                    lStrings.getString("err.servlet_config_not_initialized"));
        }

        return sc.getInitParameter(name);
    }

    /**
     * 获取init-param配置的参数名称，调用ServletConfig里的方法
     */
    public Enumeration<String> getInitParameterNames() {
        ServletConfig sc = getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(
                    lStrings.getString("err.servlet_config_not_initialized"));
        }

        return sc.getInitParameterNames();
    }

    /**
     * 返回ServletConfig
     */
    public ServletConfig getServletConfig() {
        return config;
    }

    /**
     * 返回ServletContext，调用ServletConfig里的方法
     */
    public ServletContext getServletContext() {
        ServletConfig sc = getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(
                    lStrings.getString("err.servlet_config_not_initialized"));
        }

        return sc.getServletContext();
    }

    public String getServletInfo() {
        return "";
    }

    /**
     * 实现了Servlet的init方法，保存ServletConfig然后条用无参init方法
     */
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        this.init();
    }

    /**
     * 模板方法，子类实现
     */
    public void init() throws ServletException {

    }

    /**
     * 记录日志，传递给ServletContext的日志实现
     */
    public void log(String msg) {
        getServletContext().log(getServletName() + ": " + msg);
    }

    /**
     * 记录异常，传递给ServletContext的日志实现
     */
    public void log(String message, Throwable t) {
        getServletContext().log(getServletName() + ": " + message, t);
    }

    public abstract void service(ServletRequest req, ServletResponse res)
            throws ServletException, IOException;

    public String getServletName() {
        ServletConfig sc = getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(
                    lStrings.getString("err.servlet_config_not_initialized"));
        }

        return sc.getServletName();
    }
}
```
### 1.3HttpServlet
HttpServlet是用于Http协议实现的Servlet的基类。重写类service方法，将ServletResopnse与ServletRequest转换为HttpServletResopnse与HttpServletRequest，然后根据Http请求类型的不同调用不同的方法
```java
@Override
public void service(ServletRequest req, ServletResponse res)
        throws ServletException, IOException
{
    HttpServletRequest  request;
    HttpServletResponse response;
    //如果请求类型不相符，则抛出异常
    if (!(req instanceof HttpServletRequest &&
            res instanceof HttpServletResponse)) {
        throw new ServletException("non-HTTP request or response");
    }
    // 转换request与response类型
    request = (HttpServletRequest) req;
    response = (HttpServletResponse) res;
    // 调用http的处理方法
    service(request, response);
}
protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
{
    String method = req.getMethod();
    // 根据不同类型调用不同方法
    if (method.equals(METHOD_GET)) {
        // 对是否过期进行检查,如果没过期直接304使用缓存
        long lastModified = getLastModified(req);
        if (lastModified == -1) {
            doGet(req, resp);
        } else {
            long ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
            if (ifModifiedSince < lastModified) {
                maybeSetLastModified(resp, lastModified);
                doGet(req, resp);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            }
        }

    } else if (method.equals(METHOD_HEAD)) {
        long lastModified = getLastModified(req);
        maybeSetLastModified(resp, lastModified);
        // 调用doGet(),返回Body为空的Reponse
        doHead(req, resp);
    } else if (method.equals(METHOD_POST)) {
        doPost(req, resp);
    } else if (method.equals(METHOD_PUT)) {
        doPut(req, resp);
    } else if (method.equals(METHOD_DELETE)) {
        doDelete(req, resp);
    } else if (method.equals(METHOD_OPTIONS)) {
        // 调试
        doOptions(req,resp);
    } else if (method.equals(METHOD_TRACE)) {
        // 诊断服务器
        doTrace(req,resp);
    } else {
        String errMsg = lStrings.getString("http.method_not_implemented");
        Object[] errArgs = new Object[1];
        errArgs[0] = method;
        errMsg = MessageFormat.format(errMsg, errArgs);
        resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, errMsg);
    }
}
```
HttpServlet将不同请求路由的不同方法,而SpringMVC中有将请求合并统一到一个方法进行处理.

### 1.4 HttpServletBean
>> Servlet创建的时候可以直接条用无参init方法，HttpServletBean的int方法。

```java 

@Override
public final void init() throws ServletException {
    if (logger.isDebugEnabled()) {
        logger.debug("Initializing servlet '" + getServletName() + "'");
    }

    // Set bean properties from init parameters.
    try {
        // 将Servlet中配置的参数封装到pvs变量中，requiredProperties为必须参数，如果没有报异常
        PropertyValues pvs = new ServletConfigPropertyValues(getServletConfig(), this.requiredProperties);
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(this);
        ResourceLoader resourceLoader = new ServletContextResourceLoader(getServletContext());
        bw.registerCustomEditor(Resource.class, new ResourceEditor(resourceLoader, getEnvironment()));
        // 模板方法，在子类实现,做初始化工作，
        initBeanWrapper(bw);
        // 将初始化值设置到DispatcherServlet
        bw.setPropertyValues(pvs, true);
    }
    catch (BeansException ex) {
        logger.error("Failed to set bean properties on servlet '" + getServletName() + "'", ex);
        throw ex;
    }

    // 模板方法，子类的初始化入口
    initServletBean();

    if (logger.isDebugEnabled()) {
        logger.debug("Servlet '" + getServletName() + "' configured successfully");
    }
}
```
BeanWrapper是Spring提供的一个可以操作javaBean的工具，可以直接修改javaBean的属性值。
### 1.5 FrameworkServlet
从HttpServletBean知，FrameworkServlet的入口方法为initServletBean()。
```java
@Override
protected final void initServletBean() throws ServletException {
    getServletContext().log("Initializing Spring FrameworkServlet '" + getServletName() + "'");
    if (this.logger.isInfoEnabled()) {
        this.logger.info("FrameworkServlet '" + getServletName() + "': initialization started");
    }
    long startTime = System.currentTimeMillis();

    try {
        // 初始化webApplicationContext
        this.webApplicationContext = initWebApplicationContext();
        // 初始化FrameworkServlet，模板方法子类可以实现
        initFrameworkServlet();
    }
    catch (ServletException ex) {
        this.logger.error("Context initialization failed", ex);
        throw ex;
    }
    catch (RuntimeException ex) {
        this.logger.error("Context initialization failed", ex);
        throw ex;
    }

    if (this.logger.isInfoEnabled()) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        this.logger.info("FrameworkServlet '" + getServletName() + "': initialization completed in " +
                elapsedTime + " ms");
    }
}
```
FrameworkServlet的构建过程主要就是初始化webApplicationContext().
```java
protected WebApplicationContext initWebApplicationContext() {
    // 获取rootContext
    WebApplicationContext rootContext =
            WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    WebApplicationContext wac = null;
    // 1.存在以WebApplicationContext为参数的构造函数，如果已经通过构造方法设置了webApplicationContext
    if (this.webApplicationContext != null) {
        wac = this.webApplicationContext;
        if (wac instanceof ConfigurableWebApplicationContext) {
            ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext) wac;
            if (!cwac.isActive()) {
                if (cwac.getParent() == null) {
                    cwac.setParent(rootContext);
                }
                configureAndRefreshWebApplicationContext(cwac);
            }
        }
    }
    if (wac == null) {
        // 2.当webApplicationContext已经存在在ServletContext中时，通过配置在Servlet中的contextAttribute参数获取
        wac = findWebApplicationContext();
    }
    if (wac == null) {
        // 3.webApplicationContext没有创建就创建一个。
        wac = createWebApplicationContext(rootContext);
    }

    if (!this.refreshEventReceived) {
        // refreshEventReceived没有触发调用此方法，模板方法子类实现
        onRefresh(wac);
    }
    // 根据publishContext标志是否进行设置，publishContext可以在init-param参数设置。
    if (this.publishContext) {
        // 将ApplicationContext保存到ServletContext中
        String attrName = getServletContextAttributeName();
        getServletContext().setAttribute(attrName, wac);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Published WebApplicationContext of servlet '" + getServletName() +
                    "' as ServletContext attribute with name [" + attrName + "]");
        }
    }

    return wac;
}
```
- 获取rootContext根容器的key为：org.springframework.web.context.WebApplicationContext.ROOT
在web.xml中添加一下配置，据classpath:spring/applicationContext.xml下的xml文件生成的rootContext。
```xml
<context-param>
  <param-name>contextConfigLocation</param-name>  
  <param-value>classpath:spring/applicationContext.xml</param-value>  
</context-param>
<listener>
  <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>  
</listener>
```
- 初始化WebApplicationContext
1. 构造方法获取
2. web.xml中配置了，contextAttribute属性
```xml
<servlet>
    <servlet-name>dispatcher</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextAttribute</param-name>
      <param-value>dispatcher</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>

  </servlet>
```  
3. 自己创建。一般都是这种情况
```java 
protected WebApplicationContext createWebApplicationContext(ApplicationContext parent) {
    // 获取创建类型
    Class<?> contextClass = getContextClass();
    if (this.logger.isDebugEnabled()) {
        this.logger.debug("Servlet with name '" + getServletName() +
                "' will try to create custom WebApplicationContext context of class '" +
                contextClass.getName() + "'" + ", using parent context [" + parent + "]");
    }
    // 检查创建类型
    if (!ConfigurableWebApplicationContext.class.isAssignableFrom(contextClass)) {
        throw new ApplicationContextException(
                "Fatal initialization error in servlet with name '" + getServletName() +
                "': custom WebApplicationContext class [" + contextClass.getName() +
                "] is not of type ConfigurableWebApplicationContext");
    }
    // 创建
    ConfigurableWebApplicationContext wac =
            (ConfigurableWebApplicationContext) BeanUtils.instantiateClass(contextClass);

    wac.setEnvironment(getEnvironment());
    wac.setParent(parent);
    // 将设置的contextConfigLocation参数传给wac，默认传入WEN-INFO/[ServletName]-Servlet.xml
    wac.setConfigLocation(getContextConfigLocation());

    configureAndRefreshWebApplicationContext(wac);

    return wac;
}
protected void configureAndRefreshWebApplicationContext(ConfigurableWebApplicationContext wac) {
    if (ObjectUtils.identityToString(wac).equals(wac.getId())) {
        if (this.contextId != null) {
            wac.setId(this.contextId);
        }
        else {
            wac.setId(ConfigurableWebApplicationContext.APPLICATION_CONTEXT_ID_PREFIX +
                    ObjectUtils.getDisplayString(getServletContext().getContextPath()) + '/' + getServletName());
        }
    }

    wac.setServletContext(getServletContext());
    wac.setServletConfig(getServletConfig());
    wac.setNamespace(getNamespace());
    // 添加ContextRefreshListener监听
    wac.addApplicationListener(new SourceFilteringListener(wac, new ContextRefreshListener()));

    ConfigurableEnvironment env = wac.getEnvironment();
    if (env instanceof ConfigurableWebEnvironment) {
        ((ConfigurableWebEnvironment) env).initPropertySources(getServletContext(), getServletConfig());
    }

    postProcessWebApplicationContext(wac);
    applyInitializers(wac);
    wac.refresh();
}
```
wac添加了监听器
```java
// 添加ContextRefreshListener监听
wac.addApplicationListener(new SourceFilteringListener(wac, new ContextRefreshListener()));
```
>> SourceFilteringListener可以根据输入参数不同进行选择，实际监听的是ContextRefreshListener监听的事件。ContextRefreshListener是FrameworkServlet的内部类，监听ContextRefreshedEvent事件，当接受到消息时会调用FrameworkServlet的onApplicationEvent方法，在onApplicationEvent中会将refreshEventReceived设置为true并调用onRefresh方法。

- 设置webApplicationContext并根据情况调用onRefresh方法
DispatcherServlet覆写了FrameworkServlet中的onRefresh方法.
###  1.6 DispatcherServlet
onRefresh方法是入口方法。调用了九个初始化方法。
```java 
@Override
protected void onRefresh(ApplicationContext context) {
    initStrategies(context);
}
protected void initStrategies(ApplicationContext context) {
    initMultipartResolver(context);
    initLocaleResolver(context);
    initThemeResolver(context);
    initHandlerMappings(context);
    initHandlerAdapters(context);
    initHandlerExceptionResolvers(context);
    initRequestToViewNameTranslator(context);
    initViewResolvers(context);
    initFlashMapManager(context);
}
```
initStrategies方法主要就是初始化九个组件，以initLocaleResolver为例
```java
private void initLocaleResolver(ApplicationContext context) {
    try {
        // 在容器内查找localeResolver
        this.localeResolver = context.getBean(LOCALE_RESOLVER_BEAN_NAME, LocaleResolver.class);
        if (logger.isDebugEnabled()) {
            logger.debug("Using LocaleResolver [" + this.localeResolver + "]");
        }
    }
    catch (NoSuchBeanDefinitionException ex) {
        // 容器内没有就获取默认组件
        this.localeResolver = getDefaultStrategy(context, LocaleResolver.class);
        if (logger.isDebugEnabled()) {
            logger.debug("Unable to locate LocaleResolver with name '" + LOCALE_RESOLVER_BEAN_NAME +
                    "': using default [" + this.localeResolver + "]");
        }
    }
}
```
默认组件获取
```java
protected <T> T getDefaultStrategy(ApplicationContext context, Class<T> strategyInterface) {
    List<T> strategies = getDefaultStrategies(context, strategyInterface);
    if (strategies.size() != 1) {
        throw new BeanInitializationException(
                "DispatcherServlet needs exactly 1 strategy for interface [" + strategyInterface.getName() + "]");
    }
    return strategies.get(0);
}
protected <T> List<T> getDefaultStrategies(ApplicationContext context, Class<T> strategyInterface) {
    String key = strategyInterface.getName();
    // 从defaultStrategies获取所需策略的属性
    String value = defaultStrategies.getProperty(key);
    if (value != null) {
        // 如果有多个策略以‘，’分割
        String[] classNames = StringUtils.commaDelimitedListToStringArray(value);
        List<T> strategies = new ArrayList<T>(classNames.length);
        // 按获取到的类型初始化策略
        for (String className : classNames) {
            try {
                Class<?> clazz = ClassUtils.forName(className, DispatcherServlet.class.getClassLoader());
                Object strategy = createDefaultStrategy(context, clazz);
                strategies.add((T) strategy);
            }
            catch (ClassNotFoundException ex) {
                throw new BeanInitializationException(
                        "Could not find DispatcherServlet's default strategy class [" + className +
                                "] for interface [" + key + "]", ex);
            }
            catch (LinkageError err) {
                throw new BeanInitializationException(
                        "Error loading DispatcherServlet's default strategy class [" + className +
                                "] for interface [" + key + "]: problem with class file or dependent class", err);
            }
        }
        return strategies;
    }
    else {
        return new LinkedList<T>();
    }
}
```
defaultStrategies的初始化在一个静态代码块
```java
static {
    // Load default strategy implementations from properties file.
    // This is currently strictly internal and not meant to be customized
    // by application developers.
    try {
        ClassPathResource resource = new ClassPathResource(DEFAULT_STRATEGIES_PATH, DispatcherServlet.class);
        defaultStrategies = PropertiesLoaderUtils.loadProperties(resource);
    }
    catch (IOException ex) {
        throw new IllegalStateException("Could not load 'DispatcherServlet.properties': " + ex.getMessage());
    }
}
```
defaultStrategie是DispatchServlet类所在包下的DispatcherServlet.properties文件内定义键值对。这个文件一共定义了八个组件，处理文件上传的组件没有默认配置。
## 2 请求处理
>> 本章主要介绍SpringMVC是怎么处理请求的。
### 2.1 HttpServletBean
没有处理请求的工作
### 2.2 FrameworkServlet
>> 在FrameworkServlet中重写了service，doGet、doPost、doPut、doDelete、doOptions、doTrace方法（除了doHead）。在Service中添加了PATCH类型请求的处理。doOptions和doTrace方法可以通过参数决定是自己处理还是交给父类处理。其他的请求都是交给processRequest统一处理。service和doGet方法代码：
```java
protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    HttpMethod httpMethod = HttpMethod.resolve(request.getMethod());
    if (HttpMethod.PATCH == httpMethod || httpMethod == null) {
        processRequest(request, response);
    }
    else {
        super.service(request, response);
    }
}
@Override
protected final void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    processRequest(request, response);
}
```
processRequest方法是FrameworkServlet类中在处理请求的核心：
```java
protected final void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    long startTime = System.currentTimeMillis();
    Throwable failureCause = null;
    // 获取LocaleContextHolder中原来保存的LocaleContext
    LocaleContext previousLocaleContext = LocaleContextHolder.getLocaleContext();
    // 获取当前请求的LocaleContext
    LocaleContext localeContext = buildLocaleContext(request);
    // 获取RequestContextHolder保存的RequestAttributes
    RequestAttributes previousAttributes = RequestContextHolder.getRequestAttributes();
    // 获取当前请求的ServletRequestAttributes
    ServletRequestAttributes requestAttributes = buildRequestAttributes(request, response, previousAttributes);
    // 拿到异步处理器并设置拦截器
    WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
    asyncManager.registerCallableInterceptor(FrameworkServlet.class.getName(), new RequestBindingInterceptor());
    // 将当前请求的LocaleContext与ServletRequestAttributes设置到LocaleContextHolder与RequestContextHolder
    initContextHolders(request, localeContext, requestAttributes);

    try {
        // 实际处理请求（子类实现）
        doService(request, response);
    }
    catch (ServletException ex) {
        failureCause = ex;
        throw ex;
    }
    catch (IOException ex) {
        failureCause = ex;
        throw ex;
    }
    catch (Throwable ex) {
        failureCause = ex;
        throw new NestedServletException("Request processing failed", ex);
    }finally {
        // 恢复原来的LocaleContext与ServletRequestAttributes到LocaleContextHolder和RequestContextHolder中
        resetContextHolders(request, previousLocaleContext, previousAttributes);
        if (requestAttributes != null) {
            requestAttributes.requestCompleted();
        }
        if (logger.isDebugEnabled()) {
            if (failureCause != null) {
                this.logger.debug("Could not complete request", failureCause);
            }
            else {
                if (asyncManager.isConcurrentHandlingStarted()) {
                    logger.debug("Leaving response open for concurrent processing");
                }
                else {
                    this.logger.debug("Successfully completed request");
                }
            }
        }
        // 发布ServletRequestHandledEvent消息
        publishRequestHandledEvent(request, response, startTime, failureCause);
    }
}
```
processRequest方法主要做了两件事：
- LocaleContext与RequestAttributes的设置与恢复
    - LocaleContext存放本地化信息Locale（如zh-cn）
    - RequestAttributes通过它可以set/get/remove Attribute,根据参数判断操作request还是session
    ```java
    @Override
    public void setAttribute(String name, Object value, int scope) {
        if (scope == SCOPE_REQUEST) {
            // 当调用requestCompleted方法后就会变为false不能再操作了
            if (!isRequestActive()) {
                throw new IllegalStateException(
                        "Cannot set request attribute - request is not active anymore!");
            }
            this.request.setAttribute(name, value);
        }
        else {
            HttpSession session = getSession(true);
            this.sessionAttributesToUpdate.remove(name);
            session.setAttribute(name, value);
        }
    }
    ```
    - LocaleContextHolder是抽象类，里面的方法都是statis的
    ```java
    public abstract class LocaleContextHolder {
	private static final ThreadLocal<LocaleContext> localeContextHolder =
			new NamedThreadLocal<LocaleContext>("LocaleContext");
	private static final ThreadLocal<LocaleContext> inheritableLocaleContextHolder =
			new NamedInheritableThreadLocal<LocaleContext>("LocaleContext");
    }
    ```
- 处理完发布ServletRequestHandledEvent消息
```java
private void publishRequestHandledEvent(
        HttpServletRequest request, HttpServletResponse response, long startTime, Throwable failureCause) {
    // publishEvents可以在配置Servlet时配置，默认为true
    if (this.publishEvents) {
        // 无论是否执行成功都会发布消息
        long processingTime = System.currentTimeMillis() - startTime;
        int statusCode = (responseGetStatusAvailable ? response.getStatus() : -1);
        this.webApplicationContext.publishEvent(
                new ServletRequestHandledEvent(this,
                        request.getRequestURI(), request.getRemoteAddr(),
                        request.getMethod(), getServletConfig().getServletName(),
                        WebUtils.getSessionId(request), getUsernameForRequest(request),
                        processingTime, failureCause, statusCode));
    }
}
```
记录日志的监听器
```java
@Component
public class ServletRequestHandledEventListener implements ApplicationListener<ServletRequestHandledEvent> {
    final static Log log =  LogFactory.getLog(ServletRequestHandledEventListener.class);
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        log.info("======发布事件：======"+event.getDescription());
    }
}
```
### 2.3 DispatcherServlet
>> DispatcherServlet里面执行处理的入口方法doService。doService并没有直接进行处理而是交给doDispatch进行具体处理。在进行doDispatch处理前首先判断是不是include请求，如果是则对request的Attribute做快照备份。doDispatch处理完成后进行还原。

```java
@Override
protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (logger.isDebugEnabled()) {
        String resumed = WebAsyncUtils.getAsyncManager(request).hasConcurrentResult() ? " resumed" : "";
        logger.debug("DispatcherServlet with name '" + getServletName() + "'" + resumed +
                " processing " + request.getMethod() + " request for [" + getRequestUri(request) + "]");
    }

    Map<String, Object> attributesSnapshot = null;
    // 当时include请求时，对request的Attribute做快照备份
    if (WebUtils.isIncludeRequest(request)) {
        attributesSnapshot = new HashMap<String, Object>();
        Enumeration<?> attrNames = request.getAttributeNames();
        while (attrNames.hasMoreElements()) {
            String attrName = (String) attrNames.nextElement();
            if (this.cleanupAfterInclude || attrName.startsWith("org.springframework.web.servlet")) {
                attributesSnapshot.put(attrName, request.getAttribute(attrName));
            }
        }
    }

    // 进行一些属性设置
    request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, getWebApplicationContext());
    request.setAttribute(LOCALE_RESOLVER_ATTRIBUTE, this.localeResolver);
    request.setAttribute(THEME_RESOLVER_ATTRIBUTE, this.themeResolver);
    request.setAttribute(THEME_SOURCE_ATTRIBUTE, getThemeSource());
    // FlashMap相关，用与Redirect转发时参数传递。inputFlashMap保存上次请求转发的属性，outputFlashMap保存本次请求需要转发的属性，flashMapManager进行管理
    FlashMap inputFlashMap = this.flashMapManager.retrieveAndUpdate(request, response);
    if (inputFlashMap != null) {
        request.setAttribute(INPUT_FLASH_MAP_ATTRIBUTE, Collections.unmodifiableMap(inputFlashMap));
    }
    request.setAttribute(OUTPUT_FLASH_MAP_ATTRIBUTE, new FlashMap());
    request.setAttribute(FLASH_MAP_MANAGER_ATTRIBUTE, this.flashMapManager);

    try {
        doDispatch(request, response);
    }
    finally {
        if (!WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted()) {
            // 还原快照备份
            if (attributesSnapshot != null) {
                restoreAttributesAfterInclude(request, attributesSnapshot);
            }
        }
    }
}
```
>> doDispatch的任务：1.根据request找到Handler；2.根据Handler找到HandlerAdapter；3.用HandlerAdapter处理Handler；4.调用processDispatchResult方法处理上面处理之后的结果。
```java
mappedHandler = getHandler(processedRequest);
HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
```
### 2.4 doDispatch方法
```java
protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpServletRequest processedRequest = request;
    HandlerExecutionChain mappedHandler = null;
    boolean multipartRequestParsed = false;

    WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);

    try {
        ModelAndView mv = null;
        Exception dispatchException = null;

        try {
            // 检查是不是上传请求
            processedRequest = checkMultipart(request);
            multipartRequestParsed = (processedRequest != request);

            // 1.根据request找到Handler
            mappedHandler = getHandler(processedRequest);
            if (mappedHandler == null || mappedHandler.getHandler() == null) {
                noHandlerFound(processedRequest, response);
                return;
            }

            // 2.根据Handler找到HandlerAdapter
            HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());

            // 处理GET，HEAD请求的Last-Modified
            String method = request.getMethod();
            boolean isGet = "GET".equals(method);
            if (isGet || "HEAD".equals(method)) {
                long lastModified = ha.getLastModified(request, mappedHandler.getHandler());
                if (logger.isDebugEnabled()) {
                    logger.debug("Last-Modified value for [" + getRequestUri(request) + "] is: " + lastModified);
                }
                if (new ServletWebRequest(request, response).checkNotModified(lastModified) && isGet) {
                    return;
                }
            }
            // 执行相应Interceptor的preHandler
            if (!mappedHandler.applyPreHandle(processedRequest, response)) {
                return;
            }

            // 3.用HandlerAdapter处理Handler请求
            mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
            // 如果是异步处理，直接返回
            if (asyncManager.isConcurrentHandlingStarted()) {
                return;
            }
            // 单view为空（比如,Handler返回值为void），根据request设置默认view
            applyDefaultViewName(processedRequest, mv);
            // 执行相应Interceptor的postHandler
            mappedHandler.applyPostHandle(processedRequest, response, mv);
        }
        catch (Exception ex) {
            dispatchException = ex;
        }
        catch (Throwable err) {
            dispatchException = new NestedServletException("Handler dispatch failed", err);
        }
        // 4.调用processDispatchResult方法处理上面处理之后的结果。
        processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
    }
    catch (Exception ex) {
        triggerAfterCompletion(processedRequest, response, mappedHandler, ex);
    }
    catch (Throwable err) {
        triggerAfterCompletion(processedRequest, response, mappedHandler,
                new NestedServletException("Handler processing failed", err));
    }
    finally {
        // 判断是否执行异步处理
        if (asyncManager.isConcurrentHandlingStarted()) {
            if (mappedHandler != null) {
                mappedHandler.applyAfterConcurrentHandlingStarted(processedRequest, response);
            }
        }
        else {
            // 删除上传请求资源
            if (multipartRequestParsed) {
                cleanupMultipart(processedRequest);
            }
        }
    }
}
```
>> doDispatch分为两个部分：处理请求和渲染页面。  开头定义了几个变量：
- HttpServletRequest processedRequest：实际处理时所用的request
- HandlerExecutionChain mappedHandler：处理请求的处理器链（包含处理器和对应的Interceptor）
- boolean multipartRequestParsed：是不是上传请求的标志
- ModelAndView mv：封装Model和View的容器
- Exception dispatchException：处理请求过程中的异常
>> 首先判断是否是上传请求如果是将request转换为multipartRequestParsed（HttpServletRequest的子类）。
>> 然后通过getHandler获取Handler处理器链，其中使用带了HandlerMapping，返回值HandlerExecutionChain,包含与当前request匹配的Interceptor和Handler
```java
protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
    for (HandlerMapping hm : this.handlerMappings) {
        if (logger.isTraceEnabled()) {
            logger.trace(
                    "Testing handler map [" + hm + "] in DispatcherServlet with name '" + getServletName() + "'");
        }
        HandlerExecutionChain handler = hm.getHandler(request);
        if (handler != null) {
            return handler;
        }
    }
    return null;
}
```
>> HandlerExecutionChain中执行时先执行Interceptor中的preHandler,最后执Handler.  
- doDispatch流程图
![doDispatch流程图](doDispatch.png "doDispatch流程图")
## 3 组件分析

### 3.1 HandlerMapping
![HandlerMapping继承结构图](HandlerMapping.png "HandlerMapping继承结构图")
>> HandlerMapping主要分为两个分支：AbstractUrlHandlerMapping和AbstractHandlerMethodMapping。
#### 3.1.1 AbstractHandlerMapping
>> AbstractHandlerMapping采用模板模式，获取Handler过程通过模板方法getHandlerInternal交给子类。AbstractHandlerMapping中保存了所有配置的interceptors，然后Handler可以根据从reqeust提取的lookupPath将相应的interceptors装配上去。此类的主要作用是设置拦截器。
##### 初始化
>> AbstractHandlerMapping继承WebApplicationObjectSupport，初始化的时候会调用模板方法initApplicationContext方法
```java
protected void initApplicationContext() throws BeansException {
    extendInterceptors(this.interceptors);
    detectMappedInterceptors(this.adaptedInterceptors);
    initInterceptors();
}
```
   1. extendInterceptors是模板方法，用于子类提供一个添加（或修改）interceptors的入口
   2. detectMappedInterceptors方法用于将SpringMVC容器及父容器的所有MappedInterceptor类型的Bean添加到mappedInterceptors属性。  
```java
protected void detectMappedInterceptors(List<HandlerInterceptor> mappedInterceptors) {
    mappedInterceptors.addAll(
            BeanFactoryUtils.beansOfTypeIncludingAncestors(
                    getApplicationContext(), MappedInterceptor.class, true, false).values());
}
```    
3. initInterceptors方法是初始化interceptors.将所有interceptor添加到adaptedInterceptors属性里面
```java
protected void initInterceptors() {
    if (!this.interceptors.isEmpty()) {
        for (int i = 0; i < this.interceptors.size(); i++) {
            Object interceptor = this.interceptors.get(i);
            if (interceptor == null) {
                throw new IllegalArgumentException("Entry number " + i + " in interceptors array is null");
            }
            this.adaptedInterceptors.add(adaptInterceptor(interceptor));
        }
    }
}
```
>> AbstractHandlerMapping的list类型的属性interceptors和adaptedInterceptors以及getMappedInterceptors()返回MappedInterceptor[]。
- interceptors是用于配置SringMVC的拦截器，有两种设置方式:一是注册HandlerMapping是通过属性设置，二是子类通过实现extendInterceptors方法进行修改。
- MappedInterceptor[]进行url请求匹配成功后才添加到HandlerExecutionChain。
- adaptedInterceptors不需要匹配全部添加。
##### 使用
HandlerMapping通过getHandler方法来获取处理器Handler以及拦截器Interceptor。
```java
public final HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
    // 获取handler，子类实现
    Object handler = getHandlerInternal(request);
    if (handler == null) {
        handler = getDefaultHandler();
    }
    if (handler == null) {
        return null;
    }
    // Bean name or resolved handler?
    if (handler instanceof String) {
        String handlerName = (String) handler;
        handler = getApplicationContext().getBean(handlerName);
    }
    // 添加拦截器
    HandlerExecutionChain executionChain = getHandlerExecutionChain(handler, request);
    if (CorsUtils.isCorsRequest(request)) {
        CorsConfiguration globalConfig = this.corsConfigSource.getCorsConfiguration(request);
        CorsConfiguration handlerConfig = getCorsConfiguration(handler, request);
        CorsConfiguration config = (globalConfig != null ? globalConfig.combine(handlerConfig) : handlerConfig);
        executionChain = getCorsHandlerExecutionChain(request, executionChain, config);
    }
    return executionChain;
}
```
- 获取handler：通过模板方法getHandlerInternal获取，如果没有获取默认Handler，如果Handler是String类型这在容器内查找bean
- 添加拦截器getHandlerExecutionChain方法。代码：
```java
protected HandlerExecutionChain getHandlerExecutionChain(Object handler, HttpServletRequest request) {
    // 创建变量
    HandlerExecutionChain chain = (handler instanceof HandlerExecutionChain ?
            (HandlerExecutionChain) handler : new HandlerExecutionChain(handler));

    String lookupPath = this.urlPathHelper.getLookupPathForRequest(request);
    // 添加符合要求的拦截器
    for (HandlerInterceptor interceptor : this.adaptedInterceptors) {
        if (interceptor instanceof MappedInterceptor) {
            MappedInterceptor mappedInterceptor = (MappedInterceptor) interceptor;
            // MappedInterceptor类型的拦截器只有匹配路径才添加
            if (mappedInterceptor.matches(lookupPath, this.pathMatcher)) {
                chain.addInterceptor(mappedInterceptor.getInterceptor());
            }
        }
        else {
            chain.addInterceptor(interceptor);
        }
    }
    return chain;
}
```
#### 3.1.2 AbstractUrlHandlerMapping
>> 将url与对应的Handler保存在一个map中,在getHandlerInternal方法中从map中获取handler。AbstractUrlHandlerMapping实现具体用url从map中获取handler的过程以及map初始化。这里的map就是AbstractUrlHandlerMapping中的handlerMap，另外还单独定义了处理“/”请求的处理器rootHandler。
```java
protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
    String lookupPath = getUrlPathHelper().getLookupPathForRequest(request);
    Object handler = lookupHandler(lookupPath, request);
    // 通过url寻找为空
    if (handler == null) {
        // 定义临时变量保存找到的handler
        Object rawHandler = null;
        if ("/".equals(lookupPath)) {
            rawHandler = getRootHandler();
        }
        if (rawHandler == null) {
            rawHandler = getDefaultHandler();
        }
        if (rawHandler != null) {
            // Bean name or resolved handler?
            if (rawHandler instanceof String) {
                String handlerName = (String) rawHandler;
                rawHandler = getApplicationContext().getBean(handlerName);
            }
            // 校验找到的handler是否与reuqest匹配，模板方法
            validateHandler(rawHandler, request);
            handler = buildPathExposingHandler(rawHandler, lookupPath, lookupPath, null);
        }
    }
    if (handler != null && logger.isDebugEnabled()) {
        logger.debug("Mapping [" + lookupPath + "] to " + handler);
    }
    else if (handler == null && logger.isTraceEnabled()) {
        logger.trace("No handler mapping found for [" + lookupPath + "]");
    }
    return handler;
}
```
>> 除了lookupHandler和buildPathExposingHandler其他方法都很好理解。
- lookupHandler是从map中查找handler，很多时候不能找到因为很多handler都是使用匹配模式入“/book/*”。方法代码：
```java
protected Object lookupHandler(String urlPath, HttpServletRequest request) throws Exception {
    // 直接匹配
    Object handler = this.handlerMap.get(urlPath);
    if (handler != null) {
        // handler为string，从容器中获取
        if (handler instanceof String) {
            String handlerName = (String) handler;
            handler = getApplicationContext().getBean(handlerName);
        }
        validateHandler(handler, request);
        return buildPathExposingHandler(handler, urlPath, urlPath, null);
    }
    // Pattern模式匹配，找到匹配matchingPatterns模式
    List<String> matchingPatterns = new ArrayList<String>();
    for (String registeredPattern : this.handlerMap.keySet()) {
        if (getPathMatcher().match(registeredPattern, urlPath)) {
            matchingPatterns.add(registeredPattern);
        }
        else if (useTrailingSlashMatch()) {
            if (!registeredPattern.endsWith("/") && getPathMatcher().match(registeredPattern + "/", urlPath)) {
                matchingPatterns.add(registeredPattern +"/");
            }
        }
    }
    // Pattern排序
    String bestPatternMatch = null;
    Comparator<String> patternComparator = getPathMatcher().getPatternComparator(urlPath);
    if (!matchingPatterns.isEmpty()) {
        Collections.sort(matchingPatterns, patternComparator);
        if (logger.isDebugEnabled()) {
            logger.debug("Matching patterns for request [" + urlPath + "] are " + matchingPatterns);
        }
        bestPatternMatch = matchingPatterns.get(0);
    }
    if (bestPatternMatch != null) {
        handler = this.handlerMap.get(bestPatternMatch);
        if (handler == null) {
            Assert.isTrue(bestPatternMatch.endsWith("/"));
            handler = this.handlerMap.get(bestPatternMatch.substring(0, bestPatternMatch.length() - 1));
        }
        // Bean name or resolved handler?
        if (handler instanceof String) {
            String handlerName = (String) handler;
            handler = getApplicationContext().getBean(handlerName);
        }
        validateHandler(handler, request);
        String pathWithinMapping = getPathMatcher().extractPathWithinPattern(bestPatternMatch, urlPath);

        // 之前通过sort方法进行排序，然后拿第一个作为bestPatternMatch的，不多有可能有多个Pattern的顺序相同,也即是sort方法返回为0,这里就是处理这种情况。
        Map<String, String> uriTemplateVariables = new LinkedHashMap<String, String>();
        for (String matchingPattern : matchingPatterns) {
            if (patternComparator.compare(bestPatternMatch, matchingPattern) == 0) {
                Map<String, String> vars = getPathMatcher().extractUriTemplateVariables(matchingPattern, urlPath);
                Map<String, String> decodedVars = getUrlPathHelper().decodePathVariables(request, vars);
                uriTemplateVariables.putAll(decodedVars);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("URI Template variables for request [" + urlPath + "] are " + uriTemplateVariables);
        }
        return buildPathExposingHandler(handler, bestPatternMatch, pathWithinMapping, uriTemplateVariables);
    }
    // No handler found...
    return null;
}
```
- buildPathExposingHandler方法用于给查找到的handler设置两个拦截器PathExposingHandlerInterceptor和UriTemplateVariablesHandlerInterceptor.这是两个内部拦截器，主要作用是将当前url实际匹配的Pattern、匹配条件和url模板参数设置到request属性，这样后面就可以直接从request属性获取。
```java
protected Object buildPathExposingHandler(Object rawHandler, String bestMatchingPattern,
        String pathWithinMapping, Map<String, String> uriTemplateVariables) {

    HandlerExecutionChain chain = new HandlerExecutionChain(rawHandler);
    chain.addInterceptor(new PathExposingHandlerInterceptor(bestMatchingPattern, pathWithinMapping));
    if (!CollectionUtils.isEmpty(uriTemplateVariables)) {
        chain.addInterceptor(new UriTemplateVariablesHandlerInterceptor(uriTemplateVariables));
    }
    return chain;
}
private class PathExposingHandlerInterceptor extends HandlerInterceptorAdapter {
    private final String bestMatchingPattern;
    private final String pathWithinMapping;

    public PathExposingHandlerInterceptor(String bestMatchingPattern, String pathWithinMapping) {
        this.bestMatchingPattern = bestMatchingPattern;
        this.pathWithinMapping = pathWithinMapping;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        exposePathWithinMapping(this.bestMatchingPattern, this.pathWithinMapping, request);
        request.setAttribute(INTROSPECT_TYPE_LEVEL_MAPPING, supportsTypeLevelMappings());
        return true;
    }

}
private class UriTemplateVariablesHandlerInterceptor extends HandlerInterceptorAdapter {
    private final Map<String, String> uriTemplateVariables;

    public UriTemplateVariablesHandlerInterceptor(Map<String, String> uriTemplateVariables) {
        this.uriTemplateVariables = uriTemplateVariables;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        exposeUriTemplateVariables(this.uriTemplateVariables, request);
        return true;
    }
}
```
>> map初始化是通过registerHandler方法进行，这个方法不是父类调用是其子类调用。这样不同子类可以通过注册不同的handler。AbstractUrlHandlerMapping有两个registerHandler方法一个是注册多个url到一个处理器，实现就是遍历url调用第二个进行注册。另一个注册过程是，先看原来map有没有传入url没有就添加进去，如果有就看handler是否是同一个如果不是就有问题。还有就是不放“/”或者"/*".
```java
protected void registerHandler(String urlPath, Object handler) throws BeansException, IllegalStateException {
    Assert.notNull(urlPath, "URL path must not be null");
    Assert.notNull(handler, "Handler object must not be null");
    Object resolvedHandler = handler;

    // Eagerly resolve handler if referencing singleton via name.
    if (!this.lazyInitHandlers && handler instanceof String) {
        String handlerName = (String) handler;
        if (getApplicationContext().isSingleton(handlerName)) {
            resolvedHandler = getApplicationContext().getBean(handlerName);
        }
    }

    Object mappedHandler = this.handlerMap.get(urlPath);
    if (mappedHandler != null) {
        if (mappedHandler != resolvedHandler) {
            throw new IllegalStateException(
                    "Cannot map " + getHandlerDescription(handler) + " to URL path [" + urlPath +
                    "]: There is already " + getHandlerDescription(mappedHandler) + " mapped.");
        }
    }
    else {
        if (urlPath.equals("/")) {
            if (logger.isInfoEnabled()) {
                logger.info("Root mapping to " + getHandlerDescription(handler));
            }
            setRootHandler(resolvedHandler);
        }
        else if (urlPath.equals("/*")) {
            if (logger.isInfoEnabled()) {
                logger.info("Default mapping to " + getHandlerDescription(handler));
            }
            setDefaultHandler(resolvedHandler);
        }
        else {
            this.handlerMap.put(urlPath, resolvedHandler);
            if (logger.isInfoEnabled()) {
                logger.info("Mapped URL path [" + urlPath + "] onto " + getHandlerDescription(handler));
            }
        }
    }
}
```
#### 3.1.3 SimpleUrlHandlerMapping
>> 其中定义了一个map变量urlMap，作用为方便配置以及在注册前做一下预处理，将所有url和handler的对应关系放在里面，最后注册前做一些预处理。
>> SimpleUrlHandlerMapping重写了父类initApplicationContext，调用registerHandlers方法完成handler注册。
```java
private final Map<String, Object> urlMap = new LinkedHashMap<String, Object>();
public void initApplicationContext() throws BeansException {
    super.initApplicationContext();
    registerHandlers(this.urlMap);
}
protected void registerHandlers(Map<String, Object> urlMap) throws BeansException {
    if (urlMap.isEmpty()) {
        logger.warn("Neither 'urlMap' nor 'mappings' set on SimpleUrlHandlerMapping");
    }
    else {
        for (Map.Entry<String, Object> entry : urlMap.entrySet()) {
            String url = entry.getKey();
            Object handler = entry.getValue();
            // Prepend with slash if not already present.
            if (!url.startsWith("/")) {
                url = "/" + url;
            }
            // Remove whitespace from handler bean name.
            if (handler instanceof String) {
                handler = ((String) handler).trim();
            }
            registerHandler(url, handler);
        }
    }
}
```
#### 3.1.3 AbstractDetectingUrlHandlerMapping
>> 这个类也通过重写initApplicationContext来注册handler里面调用detectHandlers()，在detectHandlers中根据配置的detectHandlersInAncestorContexts参数从springMVC容器及其父容器找到所有的bean的beanName，然后用determineUrlsForHandler方法对每个beanName解析出对应的urls，如果解析不为空就注册到父类map。determineUrlsForHandler是模板方法子类实现。
```java
public void initApplicationContext() throws ApplicationContextException {
    super.initApplicationContext();
    detectHandlers();
}
protected void detectHandlers() throws BeansException {
    if (logger.isDebugEnabled()) {
        logger.debug("Looking for URL mappings in application context: " + getApplicationContext());
    }
    String[] beanNames = (this.detectHandlersInAncestorContexts ?
            BeanFactoryUtils.beanNamesForTypeIncludingAncestors(getApplicationContext(), Object.class) :
            getApplicationContext().getBeanNamesForType(Object.class));

    // Take any bean name that we can determine URLs for.
    for (String beanName : beanNames) {
        String[] urls = determineUrlsForHandler(beanName);
        if (!ObjectUtils.isEmpty(urls)) {
            // URL paths found: Let's consider it a handler.
            registerHandler(urls, beanName);
        }
        else {
            if (logger.isDebugEnabled()) {
                logger.debug("Rejected bean name '" + beanName + "': no URL paths identified");
            }
        }
    }
}
```
