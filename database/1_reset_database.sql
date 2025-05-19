-- Kustutab public schema (mis põhimõtteliselt kustutab kõik tabelid)
DROP SCHEMA IF EXISTS training CASCADE;
-- Loob uue public schema vajalikud õigused
CREATE SCHEMA training
-- taastab vajalikud andmebaasi õigused
    GRANT ALL ON SCHEMA training TO postgres;
GRANT ALL ON SCHEMA training TO PUBLIC;