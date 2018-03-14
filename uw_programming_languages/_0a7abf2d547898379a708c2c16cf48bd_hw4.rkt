
#lang racket

(provide (all-defined-out)) ;; so we can put tests in a second file
;; put your code below

(define (sequence low high stride)
  (if (> low high)
      null
      (cons low (sequence (+ low stride) high stride))))

(define (string-append-map xs suffix)
  (map (lambda (x)
         (string-append x suffix))
       xs))

(define (list-nth-mod xs n)
  (if (< n 0) (error "list-nth-mod: negative number")
      (if (null? xs) (error "list-nth-mod: empty list")
          (car (list-tail xs (remainder n (length xs)))))))

(define (stream-for-n-steps s n)
  (letrec ([p (s)])
    (if (= n 0) null
      (cons (car p) (stream-for-n-steps (cdr p) (- n 1))))))
  
(define funny-number-stream          
    (letrec ([f (lambda (x) (cons (if (= 0 (remainder x 5)) (- x) x) (lambda () (f (+ x 1)))))])
      (lambda () (f 1))))

(define dan-then-dog
  (letrec ([f (lambda (x) (cons x (lambda () (f (if (string=? x "dan.jpg") "dog.jpg" "dan.jpg")))))])
    (lambda () (f "dan.jpg"))))

(define (stream-add-zero s)
  (letrec ([p (s)])
    (lambda () (cons
                (cons 0 (car p)) (stream-add-zero (cdr p))))))

(define (cycle-lists xs ys)
  (letrec ([f (lambda (n) (cons (cons (list-nth-mod xs n) (list-nth-mod ys n)) (lambda () (f (+ n 1)))))])
    (lambda () (f 0))))

(define (vector-assoc v vec)
  (letrec ([len (vector-length vec)]
    [f (lambda (n) (if (= n len) #f
               (letrec ([tmp (vector-ref vec n)])
                (if (pair? tmp) (if (equal? (car tmp) v) tmp (f (+ n 1))) (f (+ n 1)) ))))])
    (f 0)))

(define (cached-assoc xs n)
  (letrec ([vec (make-vector n #f)]
           [catch 0])
    (lambda (v) (letrec ([val (vector-assoc v vec)])
                 (if (equal? val #f)
                    (letrec ([tmp (assoc v xs)])
                      (begin (vector-set! vec catch tmp)
                             (set! catch (if (= catch (- n 1)) 0 (+ 1 catch)))
                             tmp))
                    val)))))
                      
(define-syntax while-less                           
    (syntax-rules (do)
      [(while-less e1 do e2)
       (letrec ([p e1]
                [f (lambda (x)
                     (if (< x p) (f e2) #t))])
         (f e2))]))
         
         
           
                
  