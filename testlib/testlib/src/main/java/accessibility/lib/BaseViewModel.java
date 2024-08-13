package accessibility.lib;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel extends ViewModel {

    /* renamed from: 刻槒唱镧詴 */
//    public HttpClient f19426;

    /* renamed from: 肌緭 */
//    public BaseLiveData<ViewModelStatus> f12057 = new BaseLiveData<>();

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public LifecycleOwner f19427;

    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        this.f19427 = null;

    }

//    public void m20230(String str) {
//        this.f12057.setValue(ViewModelStatus.m14666(str));
//    }
//
//    /* renamed from: 垡玖 */
//    public void m13582(LifecycleOwner lifecycleOwner) {
//        this.f19427 = lifecycleOwner;
//    }

    /* renamed from: 旞莍癡 */
//    public void m20231() {
//        this.f12057.setValue(ViewModelStatus.m23553());
//    }

//    /* renamed from: 灞酞輀攼嵞漁綬迹 */
//    public <T> void m20232(AbstractC3733<Result<T>> abstractC3733, InterfaceC4081<T> interfaceC4081) {
//        if (this.f19426 == null) {
//            this.f19426 = new HttpClient();
//        }
//        this.f19426.m25016(abstractC3733, interfaceC4081);
//    }

    /* renamed from: 祴嚚橺谋肬鬧舘 */
//    public void m20233() {
//        this.f12057.setValue(ViewModelStatus.m23552());
//    }


    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public LifecycleOwner m20234() {
        return this.f19427;
    }

//    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
//    public BaseLiveData<ViewModelStatus> m20235() {
//        return this.f12057;
//    }
}
