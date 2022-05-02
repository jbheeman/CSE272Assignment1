import java.util.Optional;

public class JournalModel {
    Optional<String> _i;
    Optional<String> _u;
    Optional<String> _s;
    Optional<String> _m;
    Optional<String> _t;
    Optional<String> _p;
    Optional<String> _w;
    Optional<String> _a;

    enum Headers {
        I(".I"), U(".U"), S(".S"), M(".M"), T(".T"), P(".P"), W(".W"), A(".A");
        private String _h;
        Headers(String h) {
            this._h = h;
        }

        @Override
        public String toString() {
            return _h;
        }
    }

    JournalModel(Optional<String> i, Optional<String> u, Optional<String> s, Optional<String> m, Optional<String> t, Optional<String> p, Optional<String> w, Optional<String> a) {
        this._i = i;
        this._u = u;
        this._s = s;
        this._m = m;
        this._t = t;
        this._p = p;
        this._w = w;
        this._a = a;
    }
}
