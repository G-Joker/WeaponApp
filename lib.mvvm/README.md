## MVVM 封装
为了以后的单元测试需要，我们使用了 MVVM 框架，利用谷歌官方的`Databinding`并且参考了MVP开源项目`AndroidFire`，我完成了一套自己的 MVVM 封装框架。下面我将简单介绍下使用和设计原理，以后会有完整的设计博客产出。

### 设计思路：面向接口和完全隐藏
MVVM 中，ViewModel 层是 View 和 Model 的中转层，View 专门用来处理 UI 的操作，Model 是一些数据实体，ViewModel 操作一些和数据处理相关的绑定操作，因为 databinding 的`双向绑定`特性，极致的封装应该是让 View 层只有绑定 ViewModel 和一些必要的 UI 操作，整体的逻辑和思路干净整齐，ViewModel 是一个个功能单一方法的集合。

MVVM 单元测试很方便，因为有了双向绑定。只需要测一下 ViewModel 的方法，方法通过了即可验证数据和 UI 逻辑。我们写代码的时候，就应该保持好设计性，尽量做到让代码的可测性很强，保持单一原则，隔离好 View 和 Model 的逻辑，让代码通过验证方法而不需要真正构造 Activity 实例就能有足够的可测行。为了让代码保持可测行，要求我们代码需要具有设计性，而代码的设计性和单一性又是单元测试的一个本身的要求，两者相互影响，相互驱动。

**这就是测试驱动开发。**

好了，现在我们代码写的也设计性了，方法也够单一了，但单元测试的时候，ViewModel 作为 View 和 Model 的桥梁，它实际上应该持有 View 和 Model 的引用的，可是单元测试构造 Activity 对象不方便，我们既然是要使用单元测试，就应该尽量避免需要打开页面这样的操作，虽然我们有一些非常强大的第三方单元测试框架能够构造 Activity 和 Fragment 甚至可以验证一些 UI 的操作，但总而言之还是一个比较麻烦而妥协的做法，所以我根据`AndroidFire`进行了 MVVM 的改造，实现了编译期的多态，通过反射构造类型参数的具体对象，在 Contact 中定义各个层级的接口，ViewModel 进行跨层调用的时候，只关注具体接口的形式，而不关心接口的具体实现和到底是哪个实例实现了他。

同时，隐藏了 databinding 的绑定操作，集成了一些`ListView`，`RecyclerView`，`ViewPager`的databinding 使用库，即时开发者不了解 databinding，按照我们封装的操作流程，开发界面就像堆砖块一样简单高效。

**同时，因为面向接口，单元测试的时候，也只需要自己构建出一个空实例，即可跳过一些 View 层的 UI 操作，做到真正意义上的单元测试。**

说的很抽象，下一节我们来看一下具体代码。

### 举个栗子！
我们先来看下封装的一些基类设计思路。因为项目是使用插件化，所有界面基本都是使用 Fragment 来展示，所以来看一下基类 Fragment
```java
public abstract class BaseFragment<VM extends BaseViewModel<? extends BaseView, ? extends BaseModel>,
        M extends BaseModel>
        extends Fragment
        implements BaseView {}
```
卧槽，这是什么。。看着这么多泛型叠加，是不是有点头晕，别急，我们从后往前慢慢看。

`BaseView` 是一个接口，里面定义了一些必须要实现的方法，比如databinding 需要的`BR`文件，`init`初始化方法等，最重要的是定义了一个基类类型，表示项目中所有的 Fragment 都是这个接口类型，辅助编译期检查。

`M extends BaseModel`：定义具体的 Model 类型。

`VM extends BaseViewModel<? extends BaseViewModel<? extends BaseView,? extends BaseModel>>`： VM 的泛型是比较复杂的，Android 中的列表控件都是需要一个 Adapter ，为了管理这些列表 item 的 VM，并且做到统一处理，所以 BaseViewModel 中的两个泛型类型都是没有 extends 来限制范围的，那么为了区分是页面 VM 还是 item 的 VM，在 BaseFragment 中，通过通配符来限定范围，在编译期提醒开发者。

好了，看好了类的定义代码，我们来下最关键的`onCreateView()`方法：
```java
 @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return initFragment(inflater, container);
    }
```
继续跟进`initFragment`方法：
```java
private View initFragment(LayoutInflater inflater, ViewGroup container) {
	if (mViewDataBinding == null) {
	    mContext = getActivity();
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);

       //反射生成泛型类对象
        mViewModel = TUtil.getT(this, 0);
        M model = TUtil.getT(this, 1);

       //VM 和 View 绑定
       if (mViewModel != null) {
	       mViewModel.setContext(mContext);
           try {
	           Method setModel = mViewModel.getClass().getMethod("setModel",Object.class);
               Method attachView = mViewModel.getClass().getMethod("attachView", Object.class);
               setModel.invoke(mViewModel, model);
               attachView.invoke(mViewModel, this);
           } catch (Exception e) {
               e.printStackTrace();
           }
      }

       //Model 和 VM 绑定
       if (model != null) {
	       model.attachViewModel(mViewModel);
       }

       //DataBinding 绑定
       mViewDataBinding.setVariable(getBR(), mViewModel);

       initView();
 }
```
这里有一些 databinding 的绑定操作，就不多细说了，我们来看下中间的部分。

```java
mViewModel = TUtil.getT(this,0);
M model = TUtil.getT(this,1);
```
这里的 mViewModel 的类型实际上是 VM，`TUtil.getT(this,0)`方法的第二个参数传入的是类上定义的泛型位置，比如 VM 在 BaseFragment 中的位置是第一个，那么就传入 0，M 是第二个，那么就传入 1 。该方法将返回具体泛型参数类型的实例。这样做的好处就是我们不需要手动操作构建对象并将引用保存到成员变量上了，只需要定义好具体类型参数的泛型类型，即可通过`getViewModel`获取 ViewModel 的具体实例。

继续看代码。`model.attachViewModel`将 ViewModel 绑定到 Model，ViewModel 和 View 的绑定以及将 Model 绑定到 ViewModel 是中间一段代码做到的：
```java 
Method setModel = mViewModel.getClass().getMethod("setModel",Object.class);
Method attachView = mViewModel.getClass().getMethod("attachView", Object.class);
setModel.invoke(mViewModel, model);
attachView.invoke(mViewModel, this);
```
通配符实际上是一种具体但未知类型的类型。ViewModel 的`attachView`和`setModel`方法的参数都是泛型参数，所以这里必须通过反射来获取具体的方法实例，再通过`invoke`进行调用方法。

我们来看一个具体使用封装的栗子，比如现在项目中的我的界面，用这个封装框架来写界面的时候，先写一个接口定义类 Contact ：
```java
interface MineContact{
    interface View extends BaseView{
	void testType();
    }
	
    abstract class ViewModel extends BaseViewModel<View,MineModel>{
	void onHttpResponse();
	void onHttpError();
    }

    abstract class Model extends BaseModel<ViewModel>{
	void loadData();
    }

}
```
这里定义了 MVVM 三层的类型和接口。当你需要添加接口的时候，只需要在这里添加即可。下面是`MineFragment`、`MineViewModel`、`MineModel`的类定义：
```java
//View
public class MineFragment extends BaseFragment<MineViewModel,MineModel> implements MineContact.View{

    private ShareView mShareView;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
     
    }

    @Override
    public int getBR() {
        return com.weapon.joker.app.mine.BR.model;
    }

    @Override
    public void testType(){
		
    }
}

//ViewModel
public class MineViewModel extends MineContact.ViewModel{

    public void init(){
        setTestString("反射封装测试成功");
        getView().testType();
        getModel.loadData();
    }

    @Bindable
    public String getTestString(){
        return getModel().testString;
    }

    public void setTestString(String testString){
        getModel().testString = testString;
        notifyPropertyChanged(BR.testString);
    }

    public void onHttpResponse(){}
    public void onHttpError(){}
}

//Model
public class MineModel extends MineContact.Model{
    @Bindable
    public String testString;

    public void loadData(){
	getViewModel().onHttpResponse();
	getViewModel().onHttpError();
    }
}

```
我们可以看到我们写具体类中，所有类的集成格式是一样的，并且我们内部可以通过我们刚刚在 Contact 中定义的接口进行各个层级之间的通信，在编译期，我们并不用关心各个接口具体的实现是什么，具体的实现将被移步到运行期中，这极大的方便了我们的单元测试，这也是多态和里式替换原则的应用。同时我们发现 MVVM 的很多操作在 ViewModel 层都被隐藏了，如果你想使用 BR 文件，就自己定义相对应的 get 方法，并不需要具体的保存一个 model 的成员变量了。下面我们来看看具体的单元测试该怎么写：

比如我们现在要测试 VM 中的 init 方法，其中的 View 接口 testType() 是一个吐司显示，为了通过这个方法，我们如果构建一个 MineFragment 实例，无疑非常麻烦，但在我们这套封装中，我们只需要这样写即可：
```java
public class Test{
	@Test
	public void main(){
	
	MineContact.View view = new MineContact.View(){
		@Override
		public void testType() {}
			 
		@Override
		 public int getLayoutId() {
		     return 0;
		 }
			 
		@Override
		public void initView() {}
			 
		@Override
		public int getBR() {
		    return 0;
		}	
	};
		
	MineContact.Model model = new MineContact.Model(){
	    @Override
	    void loadData() {}
	};
	
	MineViewModel vm = new MineViewModel();
	
	vm.attachView(view);
	vm.setModel(model);
	
	//调用 init() 方法
	vm.init();
	
	}
}

```
我们成功的在单元测试中调用了 VM 的 init 方法，也没有构造真正的 MineFragment，只是自己定义了一个和 MineFragment 同类型的接口，因为多态的原因，VM 仍然能对其进行调用操作，我们依然不需要关心 testType() 方法内部到底是不是和 MineFragment 定义的 testType() 方法是不是一样的，因为这里都是 UI 操作，我们不需要在 MVVM 的单元测试中测试它。


