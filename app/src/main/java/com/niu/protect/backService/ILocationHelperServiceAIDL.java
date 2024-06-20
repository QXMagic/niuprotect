package com.niu.protect.backService;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
public interface ILocationHelperServiceAIDL extends IInterface {
    void onFinishBind(int i) throws RemoteException;

    public static class Default implements ILocationHelperServiceAIDL {
        @Override
        public void onFinishBind(int notiId) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ILocationHelperServiceAIDL {
        private static final String DESCRIPTOR = "com.niu.protect.backService.ILocationHelperServiceAIDL";
        static final int TRANSACTION_onFinishBind = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILocationHelperServiceAIDL asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ILocationHelperServiceAIDL)) {
                return (ILocationHelperServiceAIDL) iin;
            }
            return new Proxy(obj);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1) {
                if (code == 1598968902) {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            }
            data.enforceInterface(DESCRIPTOR);
            int _arg0 = data.readInt();
            onFinishBind(_arg0);
            reply.writeNoException();
            return true;
        }

        public static class Proxy implements ILocationHelperServiceAIDL {
            public static ILocationHelperServiceAIDL sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void onFinishBind(int notiId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(notiId);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFinishBind(notiId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ILocationHelperServiceAIDL impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ILocationHelperServiceAIDL getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
