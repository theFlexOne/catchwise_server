DROP TABLE IF EXISTS public.roles CASCADE;
CREATE TABLE public.roles (
    id serial8 NOT NULL,
    "name" varchar(255) NOT NULL,
    created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL DEFAULT now(),
    CONSTRAINT roles_name_key UNIQUE (name),
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS public.users CASCADE;
CREATE TABLE public.users (
    id serial8 NOT NULL,
    username varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    password_digest varchar(255) NOT NULL,
    created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL DEFAULT now(),
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_email_key UNIQUE (email),
    CONSTRAINT users_username_key UNIQUE (username)
);

DROP TABLE IF EXISTS public.users_roles CASCADE;
CREATE TABLE public.users_roles (
    user_id int8 NOT NULL,
    role_id int8 NOT NULL,
    CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT users_roles_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles (id)
);

