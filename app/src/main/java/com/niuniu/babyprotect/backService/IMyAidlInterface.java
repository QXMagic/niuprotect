package com.niuniu.babyprotect.backService;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
public interface IMyAidlInterface extends IInterface {
    void basicTypes(int i, long j, boolean z, float f, double d, String str) throws RemoteException;

    public static class Default implements IMyAidlInterface {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMyAidlInterface {
        private static final String DESCRIPTOR = "com.niuniu.babyprotect.backService.IMyAidlInterface";
        static final int TRANSACTION_basicTypes = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMyAidlInterface asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IMyAidlInterface)) {
                return (IMyAidlInterface) iin;
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
            long _arg1 = data.readLong();
            boolean _arg2 = data.readInt() != 0;
            float _arg3 = data.readFloat();
            double _arg4 = data.readDouble();
            String _arg5 = data.readString();
            basicTypes(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
            reply.writeNoException();
            return true;
        }

        public static class Proxy implements IMyAidlInterface {
            public static IMyAidlInterface sDefaultImpl;
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
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                Throwable th;
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(anInt);
                        try {
                            _data.writeLong(aLong);
                            _data.writeInt(aBoolean ? 1 : 0);
                            try {
                                _data.writeFloat(aFloat);
                                _data.writeDouble(aDouble);
                                _data.writeString(aString);
                                boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                                if (!_status && Stub.getDefaultImpl() != null) {
                                    Stub.getDefaultImpl().basicTypes(anInt, aLong, aBoolean, aFloat, aDouble, aString);
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th1) {
                                th = th1;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
            }
        }

        public static boolean setDefaultImpl(IMyAidlInterface impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IMyAidlInterface getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
