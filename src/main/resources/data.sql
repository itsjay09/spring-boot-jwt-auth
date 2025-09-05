DROP TABLE IF EXISTS public.user_roles CASCADE;
DROP TABLE IF EXISTS public.users CASCADE;
DROP TABLE IF EXISTS public.roles CASCADE;

CREATE TABLE public.roles (
  id   BIGSERIAL PRIMARY KEY,
  name VARCHAR(64) UNIQUE NOT NULL
);

CREATE TABLE public.users (
  id       BIGSERIAL PRIMARY KEY,
  username VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255)       NOT NULL,
  enabled  BOOLEAN            NOT NULL DEFAULT TRUE
);

CREATE TABLE public.user_roles (
  user_id BIGINT NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
  role_id BIGINT NOT NULL REFERENCES public.roles(id) ON DELETE CASCADE,
  PRIMARY KEY (user_id, role_id)
);

ALTER TABLE public.roles OWNER TO jwt_user;
ALTER TABLE public.users OWNER TO jwt_user;
ALTER TABLE public.user_roles OWNER TO jwt_user;

ALTER SEQUENCE public.roles_id_seq OWNER TO jwt_user;
ALTER SEQUENCE public.users_id_seq OWNER TO jwt_user;

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;
ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;

GRANT SELECT, INSERT, UPDATE, DELETE ON public.roles, public.users, public.user_roles TO jwt_user;
GRANT USAGE, SELECT, UPDATE ON SEQUENCE public.roles_id_seq, public.users_id_seq TO jwt_user;