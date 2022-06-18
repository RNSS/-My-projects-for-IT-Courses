CREATE VIEW vBookStatus AS
SELECT
b.book_id,b.book_name,b.type,b.catagory,ba.author_name
FROM book b,branch_book br,author ba
WHERE b.book_id=br.book_id and br.book_id = ba.book_id
and br.availability = 'yes' ;

