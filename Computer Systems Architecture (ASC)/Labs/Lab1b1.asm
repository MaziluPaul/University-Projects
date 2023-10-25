;1. z=1/(a*a+b*b-5)+2/(a*a-b*b+4)

ASSUME CS: code, DS : data

data SEGMENT

a db 3
b db 3
rez dw ?

data ENDS

code SEGMENT

start:
mov AX, data
mov DS, AX

mov AL, a
mul a
; AX = a*a

mov BX, AX
; BX = a*a

mov AL, b
mul b
sub AL, 5
; AX = b*b-5

add AX, BX
; AX = a*a + b*b-5
mov BX, AX
mov AX, 1
cwd
idiv BX
; AX = 1/(a*a + b*b-5)

mov CX, AX
; CX = 1/(a*a + b*b-5)

mov AL, b
mul b
; AX = b*b
mov BX, AX
mov AL, a
mul a
; AX = a*a
sub AX, BX
; AX = a*a-b*b
add AX, 4

mov BX, AX
mov AX, 2
cwd
idiv BX

add AX, CX

mov rez, AX
mov AX, 4C00h
int 21h

code ENDS
END start

