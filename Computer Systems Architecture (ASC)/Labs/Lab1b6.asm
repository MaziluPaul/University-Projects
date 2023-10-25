;6.z=(a-b*c/d)/(c+2-a/b)+5

ASSUME CS:code, DS:data

data SEGMENT

a db 4
b db 2
c db 1
d db 1
rez dw ?

data ENDS

code SEGMENT

start:
mov AX, data
mov DS, AX

mov AL, b
mul c
; AX = b*c
cwd
idiv d
; AX = b*c/d

mov BX, AX
mov AL, a
sub AX, BX
mov CX, AX
; CX = a-b*c/d

mov AL, a
idiv b
; AX = a/b
mov BX, AX

mov AL, 2
add AL, c
; AX = 2+c

sub AX, BX
; AX = 2+c-a/b

mov BX, AX
mov AX, CX
cwd
idiv BX

add AX, 5

mov rez, AX
mov AX, 4C00h
int 21h

code ENDS
END start